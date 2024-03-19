package interview.shopee;

import java.util.ArrayList;
import java.util.Arrays;
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
* suppose s length m, p length n, there are only m-n+1 cases of sub-string in s
* that can possibly match p. and if anagrams of p can be any of the sub-strings,
* then if we sort p, and sub-string, then compare them, only equal would pass
*/
public class Solution2 {
    private String sortString(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return String.valueOf(chars);
    }

    public List<Integer> findAnagrams(String s, String p) {
        ArrayList<Integer> results = new ArrayList<>();
        if (s == null || p == null || p.isEmpty() || s.isEmpty()) {
            return results;
        }
        if (p.length() > s.length()) {
            return results;
        }

        int m = s.length();
        int n = p.length();
        int i = 0;
        String sortedP = sortString(p);
        while ((i+n-1) < m) {
            String sub = sortString(s.substring(i, i+n));
            if (sub.equals(sortedP)) {
                results.add(i);
            }
            i++;
        }

        return results;
    }
}
