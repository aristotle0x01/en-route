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
}
