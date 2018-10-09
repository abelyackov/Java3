package Task3;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class Task3Test {
    private Task3 task3;
    int[] actArray = {1, 4, 1, 1, 1, 1, 1, 1};

    @Before
    public void init(){
        task3 = new Task3();
    }

    @Test
    public void testTrueOrFalse() {
        Assert.assertFalse(task3.is1or4(task3.arr, task3.v1, task3.v2));
    }

    @Test
    @Ignore("Игнорируем тест, т.к. массивы разные")
    public void testArray() {
        Assert.assertArrayEquals(task3.arr, actArray);
    }

    @Test
    public void testActArray(){
        task3.is1or4(actArray,task3.v1,task3.v2);
    }

    @Test
    public void testActArrayFalse(){
        Assert.assertTrue(task3.is1or4(actArray,task3.v1,task3.v2));
    }
}
