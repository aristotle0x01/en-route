package zest;

import java.util.*;

public class Iterator {

    public static void main(String[] args) {
        ArrayList<Integer> l = new ArrayList<>();
        l.add(1);
        l.add(2);
        l.add(3);
        for (Integer integer : l) {
            System.out.println(integer);
            if (integer == 1) {
                l.remove(integer);
            }
        }

        java.util.Iterator<Integer> it = l.iterator();
        while (it.hasNext()) {
            int v = it.next();
            System.out.println(v);
            if (v == 1) {
                it.remove();
            }
        }
    }


    public static String getStr() {
        return "ing";
    }
}
