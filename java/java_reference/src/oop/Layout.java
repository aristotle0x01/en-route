package oop;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

// -XX:-UseCompressedOops
public class Layout {

    public static Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe"); //Internal reference
        f.setAccessible(true);
        return (Unsafe) f.get(null);
    }

    public static void main0(String args[]) throws Exception {
        FieldsArrangement fa = new FieldsArrangement();
        System.out.println("object address: 0x" + Long.toHexString(VM.current().addressOf(fa)));
        System.out.println("object hashcode: 0x" + Long.toHexString(fa.hashCode()));
    }

    public static void main2(String args[]) throws Exception {
        Unsafe unsafe = getUnsafe();
        FieldsArrangement fa = new FieldsArrangement();
        Field f3 = FieldsArrangement.class.getDeclaredField("third");
        f3.setAccessible(true);
        assert unsafe.objectFieldOffset(f3) == 32;
        unsafe.putDouble(fa, 32, 2.0);

        System.out.println("field third: " + f3.get(fa));
    }

    public static void main1(String args[]) throws Exception {
        FieldsArrangement fa = new FieldsArrangement();
        System.out.println(ClassLayout.parseInstance(fa).toPrintable());
    }

    public static void main(String args[]) throws Exception {
        System.out.println(VM.current().details());

        FieldsArrangement fa = new FieldsArrangement();
        System.out.println("Current address: " + Long.toHexString(VM.current().addressOf(fa)));
        System.out.println("Current address: " + Long.toHexString(Addresser.addressOf(fa)));
        System.out.println(ClassLayout.parseInstance(fa).toPrintable());

        {
            Unsafe unsafe = getUnsafe();
            unsafe.putInt(fa, 16, 2);
            unsafe.putChar(fa, 44, 'a');

            Field f3 = FieldsArrangement.class.getDeclaredField("first");
            System.out.println("offset first: " + unsafe.objectFieldOffset(f3));
        }

        System.out.println("");
        System.out.println("Class layout:");
        System.out.println(ClassLayout.parseClass(FieldsArrangement.class).toPrintable());

//        SimpleInt instance = new SimpleInt();
//        System.out.println(ClassLayout.parseInstance(instance).toPrintable());
//        System.out.println("The identity hash code is " + System.identityHashCode(instance));
//        System.out.println(ClassLayout.parseInstance(instance).toPrintable());
//        boolean[] booleans = new boolean[3];
//        System.out.println(ClassLayout.parseInstance(booleans).toPrintable());
    }
}


class SimpleInt {
    private int state;
}

class SimpleLong extends SimpleInt {
    private char state;
}

class FieldsArrangement extends SimpleLong {
    private boolean first;
    private char second;
    private double third;
    private int fourth;
    private boolean fifth;
    private static int si;
}



















