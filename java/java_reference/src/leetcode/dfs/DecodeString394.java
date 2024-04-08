package leetcode.dfs;

import java.security.InvalidParameterException;
import java.util.Stack;

public class DecodeString394 {

    public String decodeString(String s) {
        if (s == null) {
           return s;
        }
        if (s.isEmpty()) {
            return s;
        }

        int i = 0;
        StringBuilder sb = new StringBuilder();
        Stack<Integer> timesStack = new Stack<>();
        Stack<StringBuilder> builderStack = new Stack<>();
        StringBuilder cur = sb;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (c>='a' && c<='z') {
                cur.append(c);
            } else if (c>='0' && c<='9') {
                StringBuilder timesSb = new StringBuilder();
                while (c>='0' && c<='9') {
                    timesSb.append(c);
                    c = s.charAt(++i);
                }
                i--;
                timesStack.push(Integer.valueOf(timesSb.toString()));
            } else if (c == '[') {
                builderStack.push(cur);
                cur = new StringBuilder();
            } else if (c == ']'){
                StringBuilder tmpSb = new StringBuilder();
                int times = timesStack.pop();
                for (int k=0;k<times;k++) {
                    tmpSb.append(cur);
                }
                StringBuilder pre = builderStack.pop();
                pre.append(tmpSb);
                cur = pre;
            } else {
                throw new InvalidParameterException("contains invalid parameter!");
            }
            i++;
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        DecodeString394 s = new DecodeString394();
        // "3[a2[c]]"
        System.out.println(s.decodeString("3[z]2[2[y]pq4[2[jk]e1[f]]]ef"));
    }
}
