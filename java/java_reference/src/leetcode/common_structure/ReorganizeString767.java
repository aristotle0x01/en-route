package leetcode.common_structure;

import java.util.*;

public class ReorganizeString767 {
    // top frequency needs more different chars
    // so in each step, just take off the top two each by one
    // that is, in each step, we prioritize the top frequency

    public String reorganizeString(String s) {
        if (s == null) {
            return s;
        }
        if (s.length() <= 1) {
            return s;
        }

        HashMap<Character, Integer> map = new HashMap<>();
        for (int i=0; i<s.length(); i++) {
            Character c = s.charAt(i);
            if (map.containsKey(c)) {
                map.put(c, map.get(c)+1);
            } else {
                map.put(c, 1);
            }
        }
        PriorityQueue<Pair> pq = new PriorityQueue<>((o1, o2) -> o2.freq - o1.freq);
        Iterator<Map.Entry<Character, Integer>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Character, Integer> entry = it.next();
            pq.offer(new Pair(entry.getKey(), entry.getValue()));
        }

        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            Pair p = pq.poll();
            Pair pnext = pq.poll();
            if (pnext == null) {
                if (p.freq == 1) {
                    sb.append(p.c);
                    return sb.toString();
                } else {
                    return "";
                }
            }

            sb.append(p.c);
            sb.append(pnext.c);
            if (p.freq > 1) {
                pq.offer(new Pair(p.c, p.freq-1));
            }
            if (pnext.freq > 1) {
                pq.offer(new Pair(pnext.c, pnext.freq-1));
            }
        }

        return sb.toString();
    }

    private static class Pair {
        Character c;
        int freq;

        public Pair(Character ch, int f) {
            this.c = ch;
            this.freq = f;
        }
    }
}
