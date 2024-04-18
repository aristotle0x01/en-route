package leetcode.common_structure;

import java.util.Stack;

public class AsteroidCollision735 {
    public int[] asteroidCollision(int[] asteroids) {
        if (asteroids == null) {
            return asteroids;
        }
        if (asteroids.length <= 1) {
            return asteroids;
        }

        Stack<Integer> stack = new Stack<>();
        int n = asteroids.length;
        int i = n-1;
        while (i >= 0) {
            int vi = asteroids[i];
            if (vi < 0) {
                stack.push(vi);
            } else {
                // vi > 0
                if (stack.isEmpty()) {
                    stack.push(vi);
                } else {
                    while (!stack.isEmpty()) {
                        int vs = stack.peek();
                        if (vs < 0) {
                            if (Math.abs(vs) < vi) {
                                stack.pop();
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    if (stack.isEmpty() || stack.peek() > 0) {
                        stack.push(vi);
                    } else {
                        if (Math.abs(stack.peek()) == vi) {
                            stack.pop();
                        }
                    }
                }
            }

            i--;
        }

        if (!stack.isEmpty()) {
            int[] arr = new int[stack.size()];
            int j = 0;
            while (!stack.isEmpty()) {
                arr[j++] = stack.pop();
            }
            return arr;
        }
        return new int[0];
    }
}
