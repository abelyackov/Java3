package Task3;

import java.util.ArrayList;
import java.util.Arrays;

public class Box<T extends Fruit> {
    private ArrayList<T> fruits;

    public Box(T... fruits) {
        this.fruits = new ArrayList<T>(Arrays.asList(fruits));
    }

    //метод добавления фруктов в коробку
    public void add(T... fruits) {
        this.fruits.addAll(Arrays.asList(fruits));
    }

    //метод очистки коробки от фруктов
    public void clear() {
        fruits.clear();
    }

    //метод перекладывания фруктов из одной коробки в другую
    public void transfer(Box<? super T> box) {
        box.fruits.addAll(this.fruits);
        clear();
    }
    //метод подсчета веса фруктов в коробке, если фруктов нет, вернуть 0
    public float getWeight() {
        if (fruits.size() == 0) return 0;
        float weight = 0;
        for (T fruit: fruits) weight += fruit.getWeight();
        return weight;
    }

    //метод сравнения двух коробок по весу
    public boolean compare(Box box) {
        return this.getWeight() == box.getWeight();
    }
}
