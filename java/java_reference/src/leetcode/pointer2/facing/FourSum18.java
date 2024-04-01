package leetcode.pointer2.facing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FourSum18 {
    // https://leetcode.com/problems/4sum/

    // **note:
    // 1) use while loop not for
    // 2) integer overflow

    public List<List<Integer>> fourSum(int[] nums, int target) {
        int n = nums.length;
        Arrays.sort(nums);

        List<List<Integer>> r = new ArrayList<>();
        int i1=0;
        while (i1 < n-3) {
            int v1 = nums[i1];
            long target3 = target - v1;

            int i2 = i1 + 1;
            while (i2 < n-2) {
                int v2 = nums[i2];
                long target2 = target3 - v2;

                int i3 = i2+1;
                int i4 = n-1;
                while (i3 < i4) {
                    long sum2 = nums[i3] + nums[i4];
                    if (sum2 < target2) {
                        i3++;
                    } else if (sum2 > target2) {
                        i4--;
                    } else {
                        r.add(Arrays.asList(v1, v2, nums[i3], nums[i4]));

                        int oldi3 = i3;
                        int oldi4 = i4;
                        while (i3 < oldi4 && nums[i3] == nums[oldi3]) i3++;
                        while (i4 > oldi3 && nums[i4] == nums[oldi4]) i4--;
                    }
                }

                while (i2<(n-2) && nums[i2] == v2) i2++;
            }

            while (i1<(n-3) && nums[i1] == v1) i1++;
        }

        return r;
    }
}
