package leetcode.common_structure;

import java.util.HashMap;

public class HitCounter362 {
    private final int MaxInterval = 5*60;
    private HashMap<Integer, Integer> map;

    private int lastTimestamp = 0;

    public HitCounter362() {
        map = new HashMap<>();
    }

    public void hit(int timestamp) {
        if (map.containsKey(timestamp)) {
            map.put(timestamp, map.get(timestamp)+1);
            lastTimestamp = timestamp;
            return;
        }

        int start = timestamp - MaxInterval + 1;
        int t = lastTimestamp;
        if (t > 0 && t >= start) {
            map.put(timestamp, map.get(t)+1);
        } else {
            map.put(timestamp, 1);
        }
        lastTimestamp = timestamp;
    }

    public int getHits(int timestamp) {
        if (map.containsKey(timestamp)) {
            return map.get(timestamp);
        }

        return 0;
    }
}
