package leetcode;

public class Solution88 {

    // 88
    // https://leetcode.com/problems/merge-sorted-array/description/

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int k = m+n-1;
        while (i >= 0 && j >= 0) {
            if (nums1[i] >= nums2[j]) {
                nums1[k] = nums1[i];
                i--;
            } else {
                nums1[k] = nums2[j];
                j--;
            }
            k--;
        }
        while (i >= 0){
            nums1[k] = nums1[i];
            k--;
            i--;
        }
        while (j >= 0){
            nums1[k] = nums2[j];
            k--;
            j--;
        }
    }

    public static void main(String[] args) {
        Solution88 s = new Solution88();
        int[] array1 = new int[]{1,2,3,0,0,0};
        int[] array2 = new int[]{2,5,6};
        s.merge(array1, 3, array2, 3);
        System.out.println();
    }
}
