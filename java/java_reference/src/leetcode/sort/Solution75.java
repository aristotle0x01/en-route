package leetcode.sort;

public class Solution75 {

    // 75
    // https://leetcode.com/problems/sort-colors/description/

    public void sortColors1(int[] nums) {
        int size = nums.length;
        if (size <= 1) {
          return;
        }

        int c0 = 0;
        int i = 0;
        int j;
        while (i < size) {
            if (nums[i] == 0) {
                i++;
                c0++;
                continue;
            }

            j = i + 1;
            while (j < size && nums[j] != 0) {
                j++;
            }
            if (j == size) {
                break;
            }
            int t = nums[i];
            nums[i] = 0;
            nums[j] = t;
            i++;
            c0++;
        }

        i = c0;
        while (i < size) {
            if (nums[i] == 1) {
                i++;
                continue;
            }

            j = i + 1;
            while (j < size && nums[j] != 1) {
                j++;
            }
            if (j == size) {
                break;
            }
            int t = nums[i];
            nums[i] = 1;
            nums[j] = t;
            i++;
        }
    }

    int sort(int[] nums, int k, int start) {
        int i = start;
        int size = nums.length;
        int c = 0;
        int j;
        while (i < size) {
            if (nums[i] == k) {
                i++;
                c++;
                continue;
            }

            j = i + 1;
            while (j < size && nums[j] != k) {
                j++;
            }
            if (j == size) {
                break;
            }
            int t = nums[i];
            nums[i] = k;
            nums[j] = t;
            i++;
            c++;
        }

        return c;
    }
    public void sortColors(int[] nums) {
        int size = nums.length;
        if (size <= 1) {
            return;
        }

        int c0 = sort(nums, 0, 0);
        int c1 = sort(nums, 1, c0);
    }

    public static void main(String[] args) {
        Solution75 s = new Solution75();
        // int[] array = new int[]{1,0,1};
        // int[] array = new int[]{0,0,1};
        int[] array = new int[]{2,0,1};
        s.sortColors(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}
