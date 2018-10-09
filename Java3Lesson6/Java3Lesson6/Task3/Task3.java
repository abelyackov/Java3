package Task3;

public class Task3 {
    int v1 = 1, v2 = 4;
    int[] arr = {1, 1, 1, 1, 1, 1, 1, 1};

    public boolean is1or4(int[] myArr, int v1, int v2) {
        int count = 0;
        for (int i = 0; i < myArr.length; i++) {
            if (!(myArr[i] == v1 || myArr[i] == v2))
                throw new RuntimeException("в массиве присутствует элемент отличный от 1 и 4");
            if (myArr[i] == v1) {
                count++;
            }
        }
        if (count == myArr.length || count == 0)
            return false;
        else return true;
    }
}
