package Task3;

public class Task3 {
    public static void main(String[] args) {
        Apple app1 = new Apple();
        Apple app2 = new Apple();
        Apple app3 = new Apple();
        Apple app4 = new Apple();

        Orange or1 = new Orange();
        Orange or2 = new Orange();
        Orange or3 = new Orange();

        Box<Apple> box1 = new Box<Apple>(app1, app2);
        Box<Orange> box2 = new Box<Orange>(or1, or2);
        box1.add(app4, app3);

        System.out.println(box1.compare(box2));

        Box<Orange> box3 = new Box<Orange>();
        box2.transfer(box3);
    }
}
