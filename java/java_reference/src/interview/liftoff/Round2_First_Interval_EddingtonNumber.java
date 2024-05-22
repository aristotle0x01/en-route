package interview.liftoff;

import java.util.Arrays;

public class Round2_First_Interval_EddingtonNumber {
    // there is another challenge not having enough time
    // find center line for an array of coordinates

    public int eddingtonNumber(int[] miles) {
        if (miles == null || miles.length == 0) {
            return 0;
        }

        Arrays.sort(miles);
        int n = miles.length;
        int maxMile = 0;
        for (int i=0; i<n; i++) {
            int mile = miles[i];
            int largerOrEqual = n - i;
            if (largerOrEqual >= mile) {
                if (mile >= maxMile) {
                    maxMile = mile;
                }
            }
        }

        return maxMile;
    }

    public static void main(String[] args) {
        Round2_First_Interval_EddingtonNumber tst = new Round2_First_Interval_EddingtonNumber();
//        int[] miles = new int[]{2,5,3,4,1};
        int[] miles = new int[]{3, 1, 2};
        System.out.println(tst.eddingtonNumber(miles));
    }
}
