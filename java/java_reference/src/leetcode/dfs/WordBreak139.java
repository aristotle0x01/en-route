package leetcode.dfs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordBreak139 {
    public boolean wordBreak(String s, List<String> wordDict) {
        return breakdown(s, 0, new HashSet<>(wordDict), new Boolean[s.length()]);
    }

    private boolean breakdown(String s, int index, Set<String> wordDict, Boolean[] mem) {
        if (index >= s.length()) {
            return true;
        }
        if (mem[index] != null) {
            return mem[index];
        }

        for (int end=index+1; end <= s.length(); end++) {
            String sub = s.substring(index, end);
            if (!wordDict.contains(sub)) {
                continue;
            }

            boolean ok = breakdown(s, end, wordDict, mem);
            if (ok) {
                mem[index] = true;
                return true;
            }
        }

        mem[index] = false;
        return false;
    }

    public static void main(String[] args) {
        WordBreak139 w = new WordBreak139();
        String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
        boolean b = w.wordBreak(s, Arrays.asList("a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa"));
        System.out.println(b);
    }
}
