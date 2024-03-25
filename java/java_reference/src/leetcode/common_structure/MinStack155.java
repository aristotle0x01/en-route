package leetcode.common_structure;

import java.util.Stack;

public class MinStack155 {
    // https://leetcode.com/problems/min-stack/description/

    private Stack<Integer> stack;
    private Stack<Integer> mins;

    private int min;

    public MinStack155() {
        stack = new Stack<>();
        mins = new Stack<>();
    }

    public void push(int val) {
        if (stack.isEmpty()) {
            min = val;
        } else {
            if (val < min) {
              min = val;
            }
        }
        stack.push(val);
        mins.push(min);
    }

    public void pop() {
        stack.pop();
        mins.pop();
        if (!mins.isEmpty()) {
            min = mins.peek();
        }
    }

    public int top() {
       return stack.peek();
    }

    public int getMin() {
        return mins.peek();
    }
}
