package lesson1;

import java.util.ArrayList;
import java.util.List;

public class Box <T extends Fruit>{

    public List<T> fruits;

    public Box(){
        fruits = new ArrayList<>();
    }

    public float getWeight() {
        float totalWeight = 0.0f;
        for (T fruit : fruits) {
            totalWeight += fruit.getWeight();
        }
        return totalWeight;
    }

      public void addFruit(T fruit){
        fruits.add(fruit);
    }

    public void empty(Box<T> box){
        for (T fruit : fruits) {
            box.addFruit(fruit);
        }
        fruits.clear();
    }

    public boolean compare(Box box){
        return this.getWeight() == box.getWeight();
    }
}
