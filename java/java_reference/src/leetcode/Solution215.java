package leetcode;

import java.security.InvalidParameterException;
import java.util.PriorityQueue;
import java.util.Queue;

public class Solution215 {

    // 0215
    // https://leetcode.com/problems/kth-largest-element-in-an-array/description/

    // use
    public int findKthLargest(int[] nums, int k) {
        if (k > nums.length) {
            throw new InvalidParameterException("k larger than array size");
        }

        Queue<Integer> q = new PriorityQueue();
        for (int i=0; i<k; i++) {
            q.add(nums[i]);
        }
        for (int i=k; i<nums.length; i++) {
            if (nums[i] > q.peek()) {
                q.remove();
                q.add(nums[i]);
            }
        }

        return q.peek();
    }

    public static void main(String[] args) {
        Solution215 s = new Solution215();
        int[] array = new int[]{1,2,3,4,5,6,7};
        System.out.println(s.findKthLargest(array, 3));
    }
}
