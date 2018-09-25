package ru.geekbrains.chat.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;


import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TextField msgField;

    @FXML
    TextArea chatArea;

    @FXML
    HBox bottomPanel;

    @FXML
    HBox upperPanel;

    @FXML
    HBox startPanel;

    @FXML
    HBox regPanel;

    @FXML
    TextField regLoginField;

    @FXML
    TextField regNickField;

    @FXML
    PasswordField regPasswordField;

    @FXML
    TextField loginField;

    @FXML
    PasswordField passwordField;

    @FXML
    ListView<String> clientsList;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    File history;
    FileWriter fileWriter;
    BufferedWriter bufferedWriter;
    String userLogin;


    final String IP_ADDRESS = "localhost";
    final int PORT = 8189;

    private boolean isAuthorized;

    List<TextArea> textAreas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAuthorized(false);
        textAreas = new ArrayList<>();
        textAreas.add(chatArea);
    }

    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
        if (!isAuthorized) {
            startPanel.setVisible(true);
            startPanel.setManaged(true);
            regPanel.setVisible(false);
            regPanel.setManaged(false);
            upperPanel.setVisible(false);
            upperPanel.setManaged(false);
            bottomPanel.setVisible(false);
            bottomPanel.setManaged(false);
            clientsList.setVisible(false);
            clientsList.setManaged(false);
        } else {
            startPanel.setVisible(false);
            startPanel.setManaged(false);
            regPanel.setVisible(false);
            regPanel.setVisible(false);
            upperPanel.setVisible(false);
            upperPanel.setManaged(false);
            bottomPanel.setVisible(true);
            bottomPanel.setManaged(true);
            clientsList.setVisible(true);
            clientsList.setManaged(true);
        }
    }

    public void connect() {
        try {

            socket = new Socket(IP_ADDRESS, PORT);
            socket.setSoTimeout(120000); //задаем тайм-аут для соединения в 120 секунд, если неактивен то отключаемся
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            setAuthorized(false);
            Thread thread = new Thread(() -> {
                try {

                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/authok")) {
                            setAuthorized(true);
                            break;
                        } else {
                            for (TextArea o : textAreas) {
                                o.appendText(str + "\n");
                            }
                        }
                    }

                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/")) {
                            if (str.equals("/serverclosed")) break;
                            if (str.startsWith("/clientslist ")) {
                                String[] tokens = str.split(" ");
                                Platform.runLater(() -> {
                                    clientsList.getItems().clear();
                                    for (int i = 1; i < tokens.length; i++) {
                                        clientsList.getItems().add(tokens[i]);
                                    }
                                });
                            }
                        } else {
                            String msg = str + "\n";
                            chatArea.appendText(msg);
                            writeToFile(msg); // запись сообщения в файл
                        }
                    }
                } catch (SocketTimeoutException s) { //отлавливаем ошибку разрыва соединения после простоя 120 сек
                    System.out.println("Соединение разорвано, из-за бездействия 120 сек");
                    setAuthorized(false);
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {

                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    setAuthorized(false);
                }
            });
            thread.setDaemon(true);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //метод создания файла с историей сообщений
    public void createHistory(String login) {
        try {
            String path = login + "_history.txt";
            history = new File(path);
            if (!history.exists()) {
                history.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //метод сообщений истории в файл
    public void writeToFile(String msg) throws IOException {
        FileWriter fileWriter = new FileWriter(history.getAbsoluteFile(), true);
        BufferedWriter osw = new BufferedWriter(fileWriter);
        try {
            osw.append(msg);
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //метод вывода последних 100 сообщений пользователя
    public void read100Msg(String login) {
        ArrayList<String> msg = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(login + "_history.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String strLine;
            for (int i = 0; i < 100; i++) {
                strLine = br.readLine();
                chatArea.appendText(strLine);
                chatArea.appendText("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg() throws IOException {
        try {
            out.writeUTF(msgField.getText());
            msgField.clear();
            msgField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToReg() {
        if (socket == null || socket.isClosed()) {
            connect();
        }
        try {
            out.writeUTF("/reg " + regLoginField.getText() + " " + regPasswordField.getText() + " " + regNickField.getText());
            regLoginField.clear();
            regNickField.clear();
            regPasswordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToAuth() {
        if (socket == null || socket.isClosed()) {
            connect();
        }
        try {
            out.writeUTF("/auth " + loginField.getText() + " " + passwordField.getText());
            createHistory(loginField.getText());
            read100Msg(loginField.getText());
            userLogin = loginField.getText();
            loginField.clear();
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void selectClient(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            MiniStage ms = new MiniStage(clientsList.getSelectionModel().getSelectedItem(), out, textAreas);
            ms.show();
        }
    }

    public void regPanel(ActionEvent actionEvent) {
        startPanel.setVisible(false);
        startPanel.setManaged(false);
        regPanel.setVisible(true);
        regPanel.setManaged(true);
        upperPanel.setVisible(false);
        upperPanel.setManaged(false);
        bottomPanel.setVisible(false);
        bottomPanel.setManaged(false);
        clientsList.setVisible(false);
        clientsList.setManaged(false);
    }

    public void upperPanel(ActionEvent actionEvent) {
        startPanel.setVisible(false);
        startPanel.setManaged(false);
        regPanel.setVisible(false);
        regPanel.setManaged(false);
        upperPanel.setVisible(true);
        upperPanel.setManaged(true);
        bottomPanel.setVisible(false);
        bottomPanel.setManaged(false);
        clientsList.setVisible(false);
        clientsList.setManaged(false);

    }
}
