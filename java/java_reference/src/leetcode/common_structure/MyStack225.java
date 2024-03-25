package leetcode.common_structure;

import java.util.LinkedList;
import java.util.Queue;

public class MyStack225 {
    // 225
    // https://leetcode.com/problems/implement-stack-using-queues/description/

    private Queue<Integer> q1;
    private Queue<Integer> q2;

    public MyStack225() {
        q1 = new LinkedList<>();
        q2 = new LinkedList<>();
    }

    public void push(int x) {
        q1.offer(x);
    }

    public int pop() {
        if (q1.isEmpty()) {
            throw new RuntimeException();
        }

        int i = 1;
        int count = q1.size();
        while (i<count) {
            q2.offer(q1.poll());
           i++;
        }

        int v = q1.poll();

        Queue<Integer> temp = q1;
        q1 = q2;
        q2 = temp;

        return v;
    }

    public int top() {
        int v = pop();
        push(v);
        return v;
    }

    public boolean empty() {
        return q1.isEmpty();
    }

    public static class MyStackUsingOneQueue {
        // 225
        // https://leetcode.com/problems/implement-stack-using-queues/description/

        private Queue<Integer> q;

        public MyStackUsingOneQueue() {
            q = new LinkedList<>();
        }

        public void push(int x) {
            q.offer(x);
        }

        public int pop() {
            if (q.isEmpty()) {
                throw new RuntimeException();
            }

            int i = 1;
            int count = q.size();
            while (i<count) {
                q.offer(q.poll());
               i++;
            }

            return q.poll();
        }

        public int top() {
            int v = pop();
            push(v);
            return v;
        }

        public boolean empty() {
            return q.isEmpty();
        }
    }
}