package leetcode.common_structure;

import java.util.Stack;

public class ValidParentheses20 {
    public boolean isValid(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                char p = stack.pop();
                if (c == ')') {
                    if (p != '(') {
                        return false;
                    }
                } else if (c == '}') {
                    if (p != '{') {
                        return false;
                    }
                } else {
                    if (p != '[') {
                        return false;
                    }
                }
            }
        }

        return stack.isEmpty();
    }
}
