package leetcode.sort;

import java.util.Arrays;

public class BubbleSort {
    // 冒泡排序的关键在于外循环控制循环次数，内循环控制比较范围
    static void bubbleSort(int array[]) {
        int size = array.length;
        if (size <= 1) return;

        int loop = size - 1;
        int i = 0;
        while (i < loop) {
            for (int j=0; j < (size-i-1); j++){
                if (array[j] > array[j+1]) {
                    int a = array[j+1];
                    array[j+1] = array[j];
                    array[j] = a;
                }
            }

            i++;
        }
    }

    public static void main(String args[]) {
        int[] data = { 10, 7, 4, 1, 25 };
        // 用 class 名调用方法
        BubbleSort.bubbleSort(data);

        System.out.println("Array sorted with bubble sort: ");
        System.out.println(Arrays.toString(data));
    }
}
