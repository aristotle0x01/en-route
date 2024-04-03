package leetcode.pointer2.same;

public class MaxConsecutiveOnesIII1004 {
    public int longestOnes(int[] nums, int k) {
        if (nums == null) {
            return 0;
        }
        int n = nums.length;
        if (k >= n) {
            return n;
        }

        int left = 0;
        int right = 0;
        int max = 0;
        int flippedZeros = 0;
        int countOnes = 0;
        while (left < n && right < n) {
            if (nums[right] == 1) {
                countOnes++;
                right++;
            } else {
                if (flippedZeros == k){
                    while (nums[left] == 1) {
                        left++;
                        countOnes--;
                    }
                    // nums[left] == 0, by left++, reduce flipped zeros by one
                    // to make one more flippable zero for current right
                    left++;
                    flippedZeros--;
                } else {
                    // flippedZeros < k
                    flippedZeros++;
                    right++;
                }
            }
            int sumOnes = countOnes+flippedZeros;
            if (sumOnes > max) {
                max = sumOnes;
            }
        }

        return max;
    }
}
