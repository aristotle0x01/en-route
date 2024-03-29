package hotspot.bytecode;

import java.util.Random;

class Dog {
    public void bark() {
        System.out.println("dog bark!");
    }
}

public class CockerSpaniel extends Dog {
    public static void main(String args[]) {
        Random r = new Random();
        Dog dog;
        if (r.nextInt() % 2 == 0){
            dog = new Dog();
        } else {
            dog = new CockerSpaniel();
        }
        dog.bark();
    }

    public void bark() {
        System.out.println("CockerSpaniel bark!");
    }
}
