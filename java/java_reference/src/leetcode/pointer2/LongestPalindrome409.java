package leetcode.pointer2;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class LongestPalindrome409 {

    // https://leetcode.com/problems/longest-palindrome/

    public int longestPalindrome(String s) {
        if (s == null) {
            return 0;
        }
        if (s.length() == 1) {
            return 1;
        }

        HashMap<String, Integer> map = new HashMap<>();
        for (int i=0; i<s.length(); i++) {
            String c = s.substring(i, i+1);
            if (map.containsKey(c)) {
                map.put(c, map.get(c)+1);
            } else {
                map.put(c, 1);
            }
        }

        int c = 0;
        int left = 0;
        Collection<Integer> collection = map.values();
        Iterator<Integer> it = collection.iterator();
        while (it.hasNext()) {
            int v = it.next();
            if (v % 2 == 0) {
                c = c + v;
            } else {
                c = c + v - 1;
                left = left + 1;
            }
        }

        if (left > 0) {
            c = c + 1;
        }

        return c;
    }
}
