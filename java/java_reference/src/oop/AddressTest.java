package oop;

import org.openjdk.jol.vm.VM;

// -XX:-UseCompressedOops
public class AddressTest {

    public static void main(String args[]) throws Exception {
        Class<?> t = AddressTest.class;
        System.out.println("java clazz address: 0x" + Long.toHexString(VM.current().addressOf(AddressTest.class)));
        System.out.println("                    0x" + Long.toHexString(VM.current().addressOf(t)));
        AddressTest ad = new AddressTest();
        System.out.println("instance address 0x" + Long.toHexString(VM.current().addressOf(ad)));
    }

    public static void main1(String args[]) throws Exception {
        FieldsArrangement2 fa = new FieldsArrangement2();
        System.out.println("object address:");
        System.out.println("    0x" + Long.toHexString(VM.current().addressOf(fa)));
    }
}

class FieldsArrangement2 {
    private boolean first;
    private char second;
    private double third;
}



















