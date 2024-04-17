package leetcode.treemap;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TimeBasedKeyValueStore981 {
    public static class TimeMap {
        private final HashMap<String, TreeMap<Integer, String>> timestampedKV;

        public TimeMap() {
            timestampedKV = new HashMap<>();
        }

        public void set(String key, String value, int timestamp) {
            TreeMap<Integer, String> tmap;
            if ((tmap = timestampedKV.get(key)) == null) {
                tmap = new TreeMap<>();
                timestampedKV.put(key, tmap);
            }
            tmap.put(timestamp, value);
        }

        public String get(String key, int timestamp) {
            TreeMap<Integer, String> tmap;
            if ((tmap = timestampedKV.get(key)) == null) {
                return "";
            }
            Map.Entry<Integer, String> entry = tmap.floorEntry(timestamp);
            if (entry != null) {
                return entry.getValue();
            }
            return "";
        }
    }
}
