package leetcode.dp;

import java.util.*;

public class LargestDivisibleSubset368 {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return Arrays.asList(nums[0]);
        }

        Arrays.sort(nums);
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int max = 0;
        int maxIndex = 0;
        Map<Integer, List<Integer>> dplist = new HashMap<>();
        for (int i=0; i<n; i++) {
            int vi = nums[i];
            dplist.put(i, new ArrayList<>());
            dplist.get(i).add(vi);

            int tmax = dp[i];
            int tindex = i;
            for (int j=0; j<i; j++) {
                int vj = nums[j];
                if (vi % vj == 0 && (dp[j]+1) > tmax) {
                    tmax = dp[j]+1;
                    tindex = j;
                }
            }
            if (tindex != i) {
                dplist.get(i).addAll(dplist.get(tindex));
                dp[i] = tmax;
            }
            if (dp[i] > max) {
                max = dp[i];
                maxIndex = i;
            }
        }

        return dplist.get(maxIndex);
    }
}
