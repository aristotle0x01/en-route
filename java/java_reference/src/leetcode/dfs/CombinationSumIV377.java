package leetcode.dfs;

public class CombinationSumIV377 {

    int count = 0;
    int[] nums;
    int n;
    int target;

    public int combinationSum4(int[] nums, int target) {
        n = nums.length;
        this.nums = nums;
        this.target = target;
        count = 0;

        dfs(0);
        return count;
    }

    private void dfs(int acc) {
        if (acc > target) {
            return;
        }
        if (acc == target) {
            count++;
            return;
        }

        for (int i=0; i<n; i++) {
            dfs(acc + this.nums[i]);
        }
    }

    public static void main(String[] args) {
        CombinationSumIVCachedDfs s = new CombinationSumIVCachedDfs();
        int c = s.combinationSum4(new int[]{2, 1, 3}, 35);
        // int c = s.combinationSum4(new int[]{1, 2}, 3);
        System.out.println(c);
    }

    public static class CombinationSumIVCachedDfs {
        int[] nums;
        int n;
        int target;
        Integer[] cache;

        public int combinationSum4(int[] nums, int target) {
            n = nums.length;
            this.nums = nums;
            this.target = target;

            // cache the number of ways from t -> target
            cache = new Integer[target+1];
            return dfs(0);
        }

        private int dfs(int acc) {
            // generally speaking, return the cached content when using dfs with cache
            
            if (acc > target) {
                return 0;
            }
            if (acc == target) {
                return 1;
            }

            if (cache[acc] != null) {
                return cache[acc];
            }
            cache[acc] = 0;
            for (int i=0; i<n; i++) {
                cache[acc] += dfs(acc + this.nums[i]);
            }
            return cache[acc];
        }
    }

    public static class CombinationSumIVDP {
        // 1132436852
        public int combinationSum4(int[] nums, int target) {
            int[] dp = new int[target+1];
            dp[0] = 1;
            for (int t=1; t<=target; t++) {
                for (int num : nums) {
                    int pre = t - num;
                    if (pre >= 0) {
                        dp[t] += dp[pre];
                    }
                }
            }

            return dp[target];
        }
    }
}
