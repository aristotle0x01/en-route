package bytecode;

class Baseclass {
    protected void f1() {
        System.out.println("base f1");
    }
}

public class Superclass extends Baseclass{
    private void interestingMethod() {
        System.out.println("Superclass's interesting method.");
    }

    void exampleMethod() {
        interestingMethod();
    }

    protected void f1() {
        System.out.println("super f1");
    }
}

class Subclass extends Superclass {
    private void interestingMethod() {
        System.out.println("Subclass's interesting method.");
    }

    protected void f1() {
        super.f1();
        System.out.println("sub f1");
    }

    public static void main(String args[]) {
        Superclass me = new Subclass();
        me.exampleMethod();
        me.f1();
    }
}