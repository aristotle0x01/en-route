package interview.shopee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

// 438
// https://leetcode.com/problems/find-all-anagrams-in-a-string/description/

//s = "abcdefbca",
//p = "abc"
//anagram of p in s
//
//anagrams: abc, acb, bca, bac, cab, cba
//
//index of anagrams in s
//[0, 6]

/*
* using a sliding window of string p length on string s, only patterns of
* the sub-string of s and p need to be compared
*
* first build string p's pattern by a hashmap of each unique character and its frequency,
* and maintain a dynamic hashmap of sub-string, compare and update it
*/

public class Solution3 {
    public List<Integer> findAnagrams(String s, String p) {
        ArrayList<Integer> results = new ArrayList<>();
        if (s == null || p == null || p.isEmpty() || s.isEmpty()) {
            return results;
        }
        if (p.length() > s.length()) {
            return results;
        }

        p = p.toLowerCase();
        s = s.toLowerCase();

        HashMap<Integer, Integer> pPattern = new HashMap<>();
        for (int i=0; i<p.length(); i++) {
            int k = p.charAt(i) - 'a';
            if (pPattern.containsKey(k)) {
                pPattern.put(k, pPattern.get(k)+1);
            } else {
                pPattern.put(k, 1);
            }
        }

        HashMap<Integer, Integer> sPattern = new HashMap<>();
        int m = s.length();
        int n = p.length();
        int i = 0;
        int j = 0;
        while (j < m) {
            int k = s.charAt(j) - 'a';
            if (sPattern.containsKey(k)) {
                sPattern.put(k, sPattern.get(k)+1);
            } else {
                sPattern.put(k, 1);
            }

            if ((j-i+1) < n) {
                j++;
            } else {
                if (sPattern.equals(pPattern)) {
                    results.add(i);
                }
                int ki = s.charAt(i) - 'a';
                sPattern.put(ki, sPattern.get(ki)-1);
                if (sPattern.get(ki) == 0) {
                    sPattern.remove(ki);
                }

                i++;
                j++;
            }
        }

        return results;
    }
}
