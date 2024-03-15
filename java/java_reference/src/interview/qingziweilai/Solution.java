package interview.qingziweilai;

// 4
public class Solution {
    void findMaxAndNum(int[] a) {
        if (a == null || a.length == 0) {
            System.out.println("invalid input");
            return;
        }

        int size = a.length;
        int i = 1;
        int max = a[0];
        int c = 1;
        while (i < size) {
            if (a[i] > max) {
                max = a[i];
                c = 1;
            } else if (a[i] == max) {
                c++;
            } else {
                break;
            }
            i++;
        }

        System.out.println(String.format("max: %d, num: %d", max, c));
    }

    void findMaxAndNum2(int[] a) {
        if (a == null || a.length == 0) {
            System.out.println("invalid input");
            return;
        }

        int max_index = maxIndex(a, 0, a.length-1);
        int c = 1;
        int i = max_index - 1;
        while (i >= 0) {
            if (a[i] == a[max_index]) {
                i--;
                c++;
            } else {
                break;
            }
        }
        i = max_index+1;
        while (i < a.length) {
            if (a[i] == a[max_index]) {
                i++;
                c++;
            } else {
                break;
            }
        }

        System.out.println(String.format("max: %d, num: %d", a[max_index], c));
    }

    int maxIndex(int[] a, int start, int end) {
        if (start == end) {
            return start;
        } else if ((end-start) == 1) {
            if (a[end] >= a[start]) {
                return end;
            }else {
                return start;
            }
        } else {
            int mid = start + (end-start)/2;
            if (a[mid] < a[start]) {
                return maxIndex(a, start, mid);
            } else {
                int index1 = maxIndex(a, start, mid);
                int index2 = maxIndex(a, mid, end);
                if (a[index1] >= a[index2]) {
                    return index1;
                } else {
                    return index2;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] a = new int[]{1,3,3,3,4,5,6,7,8,8,8,8,8,9,10,10,11,11,12,23,33,33,10,2,2,2,2,2,2,1,1,1};
        Solution s = new Solution();
        s.findMaxAndNum2(a);
    }
}
