package leetcode.dfs;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// recursion to the base case to see if it satisfies
// save the path and backtrack
public class GenerateParentheses22 {
    private Stack<Character> stack;
    private int max;
    private int left;
    private int right;
    // 2^(2n)
    public List<String> generateParenthesis(int n) {
        this.max = 2*n;
        this.stack = new Stack<>();
        this.left = n;
        this.right = n;

        List<String> list = new ArrayList<>();
        char[] arr = new char[this.max];
        generate(1, list, arr);
        return list;
    }

    private void generate(int n, List<String> list, char[] arr) {
        if (n > this.max) {
            return;
        }
        if (left < 0 || right < 0) {
            return;
        }
        if (left == 0 && right == 0) {
            return;
        }

        if (n == this.max) {
            if (left == 0 && right == 1) {
                char old = arr[n-1];
                arr[n-1] = ')';
                list.add(new String(arr));
                arr[n-1] = old;
            }
            return;
        }

        left--;
        char old = arr[n-1];
        arr[n-1] = '(';
        stack.push('(');
        generate(n+1, list, arr);
        arr[n-1] = old;
        stack.pop();
        left++;

        if (!stack.isEmpty()) {
            right--;
            stack.pop();
            old = arr[n-1];
            arr[n-1] = ')';
            generate(n+1, list, arr);
            stack.push('(');
            arr[n-1] = old;
            right++;
        }
    }
}
