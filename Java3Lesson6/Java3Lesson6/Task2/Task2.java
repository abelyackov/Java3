package Task2;

import java.util.Arrays;

public class Task2 {

    public int[] array4(int[] myArr, int value) {
        boolean isValue = false;
        int position = 0;

        for (int i = 0; i < myArr.length; i++) {
            if (myArr[i] == value) {
                isValue = true;
                position = i;
            }
        }
        if (isValue == false) throw new RuntimeException("В переданном массиве нет значения " + value);
        int[] newArr = new int[myArr.length - position - 1];
        for (int j = 0; j < newArr.length; j++) {
            newArr[j] = myArr[position + 1 + j];
        }
        return newArr;
    }

}
