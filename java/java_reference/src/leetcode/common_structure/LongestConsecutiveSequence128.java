package leetcode.common_structure;

import java.util.HashSet;
import java.util.Set;

public class LongestConsecutiveSequence128 {
    public int longestConsecutive(int[] nums) {
        if (nums == null) {
            return 0;
        }
        int n = nums.length;
        if (n <=1) {
            return n;
        }

        Set<Integer> set = new HashSet<>();
        int max = 0;
        Integer minV = null;
        Integer maxV = null;
        for (int i=0; i<n; i++) {
            if (minV == null || nums[i] < minV) {
                minV = nums[i];
            }
            if (maxV == null || nums[i] > maxV) {
                maxV = nums[i];
            }

            set.add(nums[i]);
        }
        while (!set.isEmpty()) {
            int key = set.stream().findFirst().get();
            int startv = Math.max(key - n + 1, minV);
            int endv = Math.min(key + n - 1, maxV);

            set.remove(key);
            int count = 1;
            int maxLoop = set.size();
            int loopCount = 0;
            for (int v=key-1; v>=startv && loopCount < maxLoop; v--) {
                if (set.contains(v)) {
                    count++;
                    set.remove(v);
                    loopCount++;
                } else {
                    break;
                }
            }
            for (int v=key+1; v<=endv && loopCount < maxLoop; v++) {
                if (set.contains(v)) {
                    count++;
                    set.remove(v);
                    loopCount++;
                } else {
                    break;
                }
            }
            if (count > max) {
                max = count;
            }
        }

        return max;
    }
}
