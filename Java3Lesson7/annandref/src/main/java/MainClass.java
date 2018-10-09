public class MainClass {
    public static void main(String[] args) {
        Car car1 = new Car("audi", "black", 7,10,500);

        car1.infoCar();
        car1.repaint("white");
        car1.infoCar();
    }
}
