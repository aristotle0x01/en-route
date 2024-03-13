package leetcode.sort;

public class Solution4 {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int[] merged = new int[m+n];
        if (merged.length == 0) {
            throw new RuntimeException("total length is zero");
        }

        int mid = merged.length / 2;
        // only up to mid+1 elements are needed
        int max = mid + 1;

        int c = 0;
        int i=0;
        int j=0;
        while (i < m && j < n && c < max) {
            if (nums1[i] < nums2[j]) {
                merged[c++] = nums1[i++];
            } else {
                merged[c++] = nums2[j++];
            }
        }
        while (i<m && c < max){
            merged[c++] = nums1[i++];
        }
        while (j<n && c < max){
            merged[c++] = nums2[j++];
        }

        if (merged.length % 2 == 0) {
            return (double) (merged[mid] + merged[mid - 1]) / 2;
        } else {
            return merged[mid] * 1.0;
        }
    }

    public static void main(String[] args) {
    }
}
