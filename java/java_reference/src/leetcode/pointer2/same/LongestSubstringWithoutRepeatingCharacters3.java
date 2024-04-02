package leetcode.pointer2.same;

import java.util.HashSet;

public class LongestSubstringWithoutRepeatingCharacters3 {
    // https://leetcode.com/problems/longest-substring-without-repeating-characters/description/

    // i, j
    // set
    // "dvdf"
    public int lengthOfLongestSubstring(String s) {
        if (s == null) {
            return 0;
        }
        int n = s.length();
        if (n <= 1) {
            return n;
        }

        int i = 0;
        int j = i;
        int max = 1;
        HashSet<Character> set = new HashSet();
        while (i < n && j < n) {
            Character cj = s.charAt(j);
            if (set.contains(cj)) {
                set.remove(s.charAt(i));
                i++;
                continue;
            }

            int sublen = j - i + 1;
            set.add(cj);
            if (sublen > max) {
                max = sublen;
            }
            j++;
        }

        return max;
    }
}
