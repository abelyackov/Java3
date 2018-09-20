package Task2;

import java.util.ArrayList;

public class MyArr<T extends Object> {
    private T[] obj;

    public MyArr(T[] о) {
        obj = о;
    }
    //метод замены двух элементов в массиве
    public void swap(T[] array, int index1, int index2) {
        try {
            T tmp1 = array[index1];
            T tmp2 = array[index2];
            array[index1] = tmp2;
            array[index2] = tmp1;
            showArr(array);
        } catch (Exception e) {
            System.out.println("Ошибка с индексами элементов массива для замены: неверный индекс " + e.getMessage());
        }
    }
    //метод вывода в консоль всех элементов массива
    public void showArr(T[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
    //метод преобразования массива в ArrayList
    public void transformArrayList(T[] array) {
        ArrayList<T> arrayList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            arrayList.add(i, array[i]);
        }
        showArrList(arrayList);
    }
    //метод вывода в консоль элементов ArrayList
    public void showArrList (ArrayList<T> list){
        System.out.println("Вывод на экран ArrayList");
        for (T o: list) {
            System.out.print(o + " ");
        }
        System.out.println();
    }

}

