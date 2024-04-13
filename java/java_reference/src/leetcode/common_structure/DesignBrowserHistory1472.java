package leetcode.common_structure;

import java.util.Stack;

public class DesignBrowserHistory1472 {
    Stack<String> stack1;
    Stack<String> stack2;

    public DesignBrowserHistory1472(String homepage) {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
        stack1.push(homepage);
    }

    public void visit(String url) {
        stack1.push(url);
        stack2.clear();
    }

    public String back(int steps) {
        while (stack1.size() > 1 && steps > 0) {
            stack2.push(stack1.pop());
            steps--;
        }

        return stack1.peek();
    }

    public String forward(int steps) {
        while (!stack2.isEmpty() && steps > 0) {
            stack1.push(stack2.pop());
            steps--;
        }

        return stack1.peek();
    }
}
