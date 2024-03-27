package interview.tripalink;

// using start as pivot
public class QuickSort2 {

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

    // start as pivot
    private int partition(int[] array, int start, int end) {
        int pivot = array[start];

        int firstLessIndex = -1;
        for (int i=end; i>start; i--) {
            if (array[i] < pivot) {
                if (firstLessIndex == -1) {
                    firstLessIndex = i;
                }
                continue;
            }

            if (firstLessIndex == -1) {
                continue;
            }

            // swap
            int tmp = array[firstLessIndex];
            array[firstLessIndex] = array[i];
            array[i] = tmp;
            firstLessIndex--;
        }

        if (firstLessIndex == -1) {
            return start;
        }

        array[start] = array[firstLessIndex];
        array[firstLessIndex] = pivot;
        return firstLessIndex;
    }

    public static void main(String[] args) {
        QuickSort2 s = new QuickSort2();
        // 5,2,3,1
        // 5,1,1,2,0,0
        // -4,0,7,4,9,-5,-1,0,-7,-1
        int[] array1 = new int[]{5,2,3,1};
        s.sort(array1);
        for (int i = 0; i < array1.length; i++) {
            System.out.print(array1[i] + " ");
        }
        System.out.println();
    }
}
