package Task2;

public class Task2 {
    public static void main(String[] args) {
        Integer numbers[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        MyArr<Integer> intOb = new MyArr<Integer>(numbers);
        intOb.showArr(numbers);
        intOb.swap(numbers, 3, 6);
        intOb.transformArrayList(numbers);

        String strings[] = {"Я", "на 3", "обучаюсь", "уровне Java."};
        MyArr<String> strOb = new MyArr<String>(strings);
        strOb.showArr(strings);
        strOb.swap(strings, 1, 2);
        strOb.transformArrayList(strings);
    }
}
