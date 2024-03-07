package leetcode;

import java.util.*;

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(nums);
 * int[] param_1 = obj.reset();
 * int[] param_2 = obj.shuffle();
 */
public class Solution384 {

    // 0384
    // https://leetcode.com/problems/shuffle-an-array/description/

    private int[] _nums;
    public Solution384(int[] nums) {
        _nums = nums;
    }

    public int[] reset() {
        return _nums;
    }

    public int[] shuffle() {
        HashSet<Integer> remset = new HashSet<>();
        Random r = new Random();
        int size = _nums.length;
        int[] shuffled = new int[size];

        int count = 0;
        do {
            while (true) {
                int temp = r.nextInt(size);
                if (remset.contains(temp)){
                    continue;
                }

                remset.add(temp);
                shuffled[count++] = _nums[temp];
                break;
            }
        }while (count < _nums.length);

        return shuffled;
    }

    public int[] shuffle2() {
        int size = _nums.length;
        int[] shuffled = new int[size];

        LinkedList<Integer> al = new LinkedList<>();
        for (int i=0; i<size; i++) {
            al.add(_nums[i]);
        }

        Random r = new Random();
        int c = 0;
        while (al.size() > 1) {
            int index = r.nextInt(al.size());
            shuffled[c++] = al.remove(index);
        }
        shuffled[c] = al.get(0);

        return shuffled;
    }
}
