package hotspot.oop;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;
import sun.misc.Unsafe;
import java.lang.reflect.Field;

// -XX:-UseCompressedOops
public class Layout1 {
    static Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe"); //Internal reference
        f.setAccessible(true);
        return (Unsafe) f.get(null);
    }

    public static void main0(String args[]) throws Exception {
        FieldsArrangement1 fa = new FieldsArrangement1();
        fa.it = new Integer(101);
        System.out.println(ClassLayout.parseInstance(fa).toPrintable());

        Unsafe unsafe = getUnsafe();
        Object o = unsafe.getObject(fa, 32);
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        // Read the value of the object header
        long mark = unsafe.getLong(o, 0);
        System.out.println("Object header mark: 0x" + Long.toHexString(mark));
        long clazz = unsafe.getLong(o, 8);
        System.out.println("Object header clazz: 0x" + Long.toHexString(clazz)); // A

        Class<?> t = Integer.class;
        System.out.println("Integer clazz: 0x" + Long.toHexString(VM.current().addressOf(Integer.class))); // B
        // A != B 是因为A是jvm内部instanceClass 在non heap区域， 而B是java Class对象地址在堆上

        // Get the base address of the object
        long baseOffset = unsafe.objectFieldOffset(Field.class.getDeclaredField("clazz"));
        Object t1 = unsafe.getObject(t, baseOffset);
        System.out.println("t1: 0x" + Long.toHexString(VM.current().addressOf(t1)));
    }

    public static void main(String args[]) throws Exception {
        Class<Data> t = Data.class;
        System.out.println(ClassLayout.parseInstance(t).toPrintable());

//        Unsafe unsafe = getUnsafe();
//        Object o = unsafe.getObject(t, 8);
//        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }

    public static void main1(String args[]) throws Exception {
        Unsafe unsafe = getUnsafe();
        FieldsArrangement1 fa = new FieldsArrangement1();
        System.out.println(ClassLayout.parseInstance(fa).toPrintable());
        Class<?> t = fa.getClass();
        System.out.println(ClassLayout.parseInstance(t).toPrintable());

        Field f3 = FieldsArrangement1.class.getDeclaredField("third");
        f3.setAccessible(true);
        System.out.println("unsafe.objectFieldOffset:");
        long offset = unsafe.objectFieldOffset(f3);
        unsafe.putDouble(fa, offset, 2.0);
        System.out.println("field third: " + f3.get(fa));
    }
}

class Data {
    private boolean first;
    private char second;

    public void t1() {
        System.out.println("-");
    }
}

class FieldsArrangement1 {
    private boolean first;
    private char second;
    private double third;
    private int fourth;
    private boolean fifth;

    public Integer it;
}





















