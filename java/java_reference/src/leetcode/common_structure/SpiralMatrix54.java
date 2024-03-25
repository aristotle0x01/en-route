package leetcode.common_structure;

import java.util.ArrayList;
import java.util.List;

public class SpiralMatrix54 {
    // https://leetcode.com/problems/spiral-matrix/description/

//    1 2 9  11
//    3 4 10 12
//    5 6 11 13
//    7 8 12 14

//    [1,2,3,4]
//    [5,6,7,8]
//    [9,10,11,12]
// [1,2,3,4,8,12,11,10,9,5,6,7]

    public List<Integer> spiralOrder(int[][] matrix) {
        return peel(matrix, 0, 0, matrix.length-1, matrix[0].length-1);
    }

    // left top, right bottom: (li, lj) => (ri, rj)
    private List<Integer> peel(int[][] matrix, int li, int lj, int ri, int rj) {
        List<Integer> list = new ArrayList<>();
        if (li > ri || lj > rj) {
            return list;
        }
        if (li == ri && lj == rj) {
            list.add(matrix[li][lj]);
            return list;
        }

        for (int j=lj; j<=rj; j++) {
            list.add(matrix[li][j]);
        }
        for (int i=li+1; i<=ri; i++) {
            list.add(matrix[i][rj]);
        }
        // be careful of (ri > li), otherwise may add redundant elements
        for (int j=rj-1; (ri > li) && (j>=lj); j--) {
            list.add(matrix[ri][j]);
        }
        // (rj > lj)
        for (int i=ri-1;(rj > lj) && (i>li); i--) {
            list.add(matrix[i][lj]);
        }
        //  (1,1) (1,2) => (2,2) (0,1)
        list.addAll(peel(matrix, li+1, lj+1, ri-1, rj-1));
        return list;
    }
}
