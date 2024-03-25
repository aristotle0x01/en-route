package leetcode.common_structure;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class FirstUniqueNumber1429 {
    private Queue<Integer> queue;
    private Set<Integer> unique;
    private Queue<Integer> uniques;

    public FirstUniqueNumber1429(int[] nums) {
        queue = new LinkedList<>();
        unique = new HashSet<>();
        uniques = new LinkedList<>();
        for (int num : nums) {
            add(num);
        }
    }

    public int showFirstUnique() {
        while (!uniques.isEmpty()) {
            int v = uniques.peek();
            if (unique.contains(v)) {
                return v;
            } else {
                uniques.poll();
            }
        }

        return -1;
    }

    public void add(int value) {
        queue.offer(value);
        if (!unique.contains(value)) {
            uniques.offer(value);
        } else {
            unique.remove(value);
        }
    }
}
