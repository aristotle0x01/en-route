package interview.tripalink;

public class QuickSort {

    public void sort(int[] array) {
        if (array == null || array.length == 1) {
            return;
        }

        innerSort(array, 0, array.length-1);
    }

    private void innerSort(int[] array, int start, int end) {
        if (start >= end) {
            return;
        }

        int index = partition(array, start, end);
        innerSort(array, start, index-1);
        innerSort(array, index+1, end);
    }

    // end as pivot
    private int partition(int[] array, int start, int end) {
        int pivot = array[end];

        int firstLargerIndex = -1;
        for (int i=start; i<end; i++) {
            if (array[i] >= pivot) {
                if (firstLargerIndex == -1) {
                    firstLargerIndex = i;
                }
            } else {
                if (firstLargerIndex == -1) {
                    continue;
                }

                // swap
                int tmp = array[firstLargerIndex];
                array[firstLargerIndex] = array[i];
                array[i] = tmp;
                firstLargerIndex++;
            }
        }

        if (firstLargerIndex == -1) {
            return end;
        }

        // swap of this pos may make quicksort unstable
        array[end] = array[firstLargerIndex];
        array[firstLargerIndex] = pivot;
        return firstLargerIndex;
    }

    public static void main(String[] args) {
        QuickSort s = new QuickSort();
        // 5,2,3,1
        // 5,1,1,2,0,0
        // -4,0,7,4,9,-5,-1,0,-7,-1
        int[] array1 = new int[]{5,1,1,2,0,0};
        s.sort(array1);
        for (int i = 0; i < array1.length; i++) {
            System.out.print(array1[i] + " ");
        }
        System.out.println();
    }
}
