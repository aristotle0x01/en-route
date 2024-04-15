package leetcode.pointer2.same;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class LongestSubstringWithAtMostKDistinctCharacters340 {
    // 给定一个字符串 s ，找出 至多 包含 k 个不同字符的最长子串 T。
    //    示例 1:
    //    输入: s = "eceba", k = 2
    //    输出: 3
    //    解释: 则 T 为 "ece"，所以长度为 3。
    //
    //    示例 2:
    //    输入: s = "aa", k = 1
    //    输出: 2
    //    解释: 则 T 为 "aa"，所以长度为 2。

    private int max = 0;
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        dfs2(s, 0, s.length()-1, k);
        return max;
    }

    private void dfs(String s, int start, int end, int k) {
        if (start > end) {
            return;
        }
        int range = end-start+1;
        if (range <= k) {
            if (range > max) {
                max = range;
            }
            return;
        }

        Set<Character> set = new HashSet<>();
        for (int i=start; i<=end; i++) {
            set.add(s.charAt(i));
        }

        if (set.size() <= k) {
            if (range > max) {
                max = range;
            }
        } else {
            dfs(s, start+1, end, k);
            dfs(s, start, end-1, k);
        }
    }

    private void dfs2(String s, int start, int end, int k) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i=start; i<=end; i++) {
            char c = s.charAt(i);
            if (!map.containsKey(c)) {
                if (map.keySet().size() >= k) {
                    if (i-start > max) {
                        max = i-start;
                    }

                    while (map.keySet().size() >= k && start < i) {
                        char prec = s.charAt(start);
                        start++;
                        if (map.get(prec) > 1) {
                            map.put(prec, map.get(prec)-1);
                        } else {
                            map.remove(prec);
                        }
                    }
                } else {
                    if (i-start+1 > max) {
                        max = i-start+1;
                    }
                }

                map.put(c, 1);
            } else {
                map.put(c, map.get(c)+1);
                if (i-start+1 > max) {
                    max = i-start+1;
                }
            }
        }
    }

    public static void main(String[] args) {
        LongestSubstringWithAtMostKDistinctCharacters340 s340 = new LongestSubstringWithAtMostKDistinctCharacters340();
        s340.lengthOfLongestSubstringKDistinct("eceba", 2);
        System.out.println(s340.max);
    }
}
