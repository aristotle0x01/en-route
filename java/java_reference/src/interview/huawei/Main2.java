package interview.huawei;

import java.util.ArrayList;
import java.util.Scanner;

/*
有一个总空间为100字节的堆，现要从中新申请一块内存，内存分配原则为优先紧接着前一块已使用内存分配空间足够且最接近申请大小的空闲内存。
输入：
第1行是1个整数，表示期望申请的内存字节数；
第2到N行是用空格分割的两个整数，表示当前已分配的内存的情况，每一行表示一块已分配的连续内存空间，每行的第1和第2个整数分别表示偏移地址和内存块大小，如：
0 1
3 2
表示0偏移地址开始的1个字节和3偏移地址开始的2个字节已被分配，其余内存空闲。
1.若输入信息不合法或无效，则申请失败。
2.若没有足够的空间供分配，则申请失败。
3.堆内存信息有区域重叠或有非法值等都是无效输入。
*/
public class Main2 {
    private static int MAX = 100;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int need = 0;
        ArrayList<int[]> used = new ArrayList<>();
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            need = in.nextInt();

            while (in.hasNextInt()) {
                int[] temp = new int[2];
                temp[0] = in.nextInt();
                temp[1] = in.nextInt();
                used.add(temp);
            }
            break;
        }
        if (used.isEmpty()) {
            if (need <= MAX) {
                System.out.println(0);
            } else {
                System.out.println(-1);
            }
            return;
        }
        if (used.size() == 1) {
            int e1 = used.get(0)[0] + used.get(0)[1];
            int left = MAX - e1;
            if (need <= left) {
                System.out.println(e1);
            } else {
                System.out.println(-1);
            }
            return;
        }

        ArrayList<int[]> empty = new ArrayList<>();
        for (int i=0; i<(used.size()-1); i++) {
            int e1 = used.get(i)[0] + used.get(i)[1];
            int u2 = used.get(i+1)[0];
            if (e1 < u2) {
                int[] temp = new int[2];
                temp[0] = e1;
                temp[1] = u2 - e1;
                empty.add(temp);
            }

            e1 = used.get(i+1)[0] + used.get(i+1)[1];
            if (e1 < MAX) {
                int[] temp = new int[2];
                temp[0] = e1;
                temp[1] = MAX - e1;
                empty.add(temp);
            }
        }

        int gap = MAX;
        int index = -1;
        for (int i=0; i<empty.size(); i++){
            if(empty.get(i)[1] >= need){
                int t_gap = empty.get(i)[1] - need;
                if (t_gap < gap) {
                    gap = t_gap;
                    index = empty.get(i)[0];
                }
            }
        }

        System.out.println(index);
    }
}
