package bytecode;

public class UnrelatedClass {
    public static void main(String args[]) {
        Subclass2 sc = new Subclass2(0); // invokespecial
        sc.interfaceMethod(); // invokevirtual

        InYourFace iyf = sc;
        iyf.interfaceMethod();  // invokeinterface
    }
}

interface InYourFace {
    void interfaceMethod ();
}

class Subclass1 implements InYourFace {
    Subclass1(int i) {
        super(); // invokespecial (of an <init)
    }

    public void interfaceMethod() {}

    public String toString(){
        return "-";
    }
}

class Subclass2 extends Subclass1 {
    Subclass2(int i) {
        super(i);
    }

    public String toString(){
        return "-";
    }
}
























