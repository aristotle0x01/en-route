package collection;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        // hashmap
        HashMap<Integer, String> map1 = new HashMap<>();
        Integer i0 = 0;
        int dd =  i0.hashCode();
        map1.put(null, "cc");
        map1.put(i0, "dd");

        HashMap<String, String> map = new HashMap<>();
        boolean b = map.containsKey(null);
        map.put(null, null);
        b = map.containsKey(null);
        map.put(null, "345");
        System.out.println(map.get(null));

        // hashtable
        Hashtable<String, String> table = new Hashtable();
        table.put("1", null);
        System.out.println(table.get(null));

        ConcurrentHashMap cm = new ConcurrentHashMap();
    }
}
