package leetcode.pointer2.same;

import java.util.*;

public class LongestSubstringWithAtLeastKRepeatingCharacters395 {

    // https://protegejj.gitbook.io/algorithm-practice/leetcode/two-pointers/395-longest-substring-with-at-least-k-repeating-characters

    private int max = 0;
    public int longestSubstring(String s, int k) {
        if (s == null || s.length() < k) {
            return 0;
        }

        dfs(s, 0, s.length()-1, k);
        return max;
    }

    private void dfs(String s, int i, int j, int k) {
        if (i > j) {
           return;
        }
        if ((j-i+1) < k) {
            return;
        }

        HashMap<Character, List<Integer>> map = new HashMap<>();
        for (int t=i; t<=j; t++) {
            char c = s.charAt(t);
            if (map.containsKey(c)) {
                map.get(c).add(t);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(t);
                map.put(c, list);
            }
        }

        List<Integer> indexList = new ArrayList<>();
        Iterator<Character> it = map.keySet().iterator();
        while (it.hasNext()) {
            char c = it.next();
            List<Integer> list = map.get(c);
            if (list.size() < k) {
                indexList.addAll(list);
            }
        }
        if (indexList.isEmpty()) {
            if ((j-i+1) > max) {
                max = j-i+1;
            }
            return;
        }

        int lastIndex = indexList.get(0);
        dfs(s, lastIndex+1, j, k);
        dfs(s, i, lastIndex-1, k);
    }

    public static void main(String[] args) {
        LongestSubstringWithAtLeastKRepeatingCharacters395 so = new LongestSubstringWithAtLeastKRepeatingCharacters395();
        so.longestSubstring("ababacb", 3);
        System.out.println(so.max);
    }

    // since the input can only be little case english letters
    // we can use char[26]: 'a' - 'z' for simplicity
    private static class LongestSubstringWithAtLeastKRepeatingCharacters395_array {

        private int max = 0;
        public int longestSubstring(String s, int k) {
            if (s == null || s.length() < k) {
                return 0;
            }

            dfs(s, 0, s.length()-1, k);
            return max;
        }

        private void dfs(String s, int start, int end, int k) {
            if (start > end || (end-start+1) < k) {
                return;
            }

            int[] letters = new int[26];
            Arrays.fill(letters, 0);
            for (int i=start; i<=end; i++) {
                letters[s.charAt(i)-'a']++;
            }

            for (int i=0; i<26; i++) {
                if (letters[i] > 0 && letters[i] < k) {
                    char c = (char)('a' + i);
                    int index = s.indexOf(c, start);
                    dfs(s, start, index-1, k);
                    dfs(s, index+1, end, k);
                    return;
                }
            }

            int len = end - start + 1;
            if (len > max) {
                max = len;
            }
        }

        public static void main(String[] args) {
            leetcode.pointer2.same.LongestSubstringWithAtLeastKRepeatingCharacters395 so = new leetcode.pointer2.same.LongestSubstringWithAtLeastKRepeatingCharacters395();
            so.longestSubstring("ababacb", 3);
            System.out.println(so.max);
        }
    }
}
