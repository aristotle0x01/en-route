package interview.tripalink;

import java.util.HashMap;

public class Solution {
//    给定一个字符串 s ，请你找出其中不含有重复字符的最长子串的长度。
//
//    示例 1:
//    输入: s = "pwwkew"
//    输出: 3
//    解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
//    请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
//
//    提示：
//            0 <= s.length <= 5 * 104
//    s 由英文字母、数字、符号和空格组成

    public static void main(String[] args) {
        String s = "pwwkew";
        Solution sol = new Solution();
        System.out.println(sol.maxContinueousSubString(s));
    }

    public int maxContinueousSubString(String s) {
        if (s == null) {
            return 0;
        }
        if (s.length() <= 1) {
            return s.length();
        }

        int max = 0;
        HashMap<Character, Integer> freq = new HashMap<>();
        int i = 0;
        while (i < s.length()) {
            Character c = s.charAt(i);
            if (!freq.containsKey(c)) {
                freq.put(c, 1);
                i++;
                if (freq.size() > max) {
                    max = freq.size();
                }
            } else {
                if (freq.size() > max) {
                    max = freq.size();
                }
                freq.clear();
                freq.put(c, 1);
                i++;
            }
        }

        return max;
    }
}
