package Task1;

public class MyArr<T extends Object> {
    private T[] obj;

    public MyArr(T[] о) {
        obj = о;
    }

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

    public void showArr(T[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}

