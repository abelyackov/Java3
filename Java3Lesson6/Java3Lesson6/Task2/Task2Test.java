package Task2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class Task2Test {
    private Task2 task2;
    private int[] arr1 = {1, 2, 4, 5, 6, 4, 2, 2};
    private int[] arr2 = {1, 2, 5, 6, 4, 2, 2};
    private int[] arr3 = {1, 2, 5, 4, 6, 2, 2};
    private int[] arr4 = {4, 4, 4, 4, 4, 4};
    private int value = 4;

    @Before
    public void init() {
        task2 = new Task2();
    }


    @Test(expected = RuntimeException.class)
    public void testArr1() {
        task2.array4(arr1, 7);
    }

    @Test
    @Ignore("В массивах разные значения элементов")
    public void testArr2() {
        Assert.assertArrayEquals(arr2, arr3);
    }

    @Test
    public void testArr3() {
        Assert.assertArrayEquals(task2.array4(arr3, value), new int[]{6, 2, 2});
    }

    @Test
    public void testArr4() {
        task2.array4(arr4, value);
    }


}
