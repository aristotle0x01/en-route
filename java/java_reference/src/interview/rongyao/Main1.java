package interview.rongyao;

import java.util.ArrayList;
import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int low = 0;
        int high = 0;
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            low = in.nextInt();
            high = in.nextInt();
            break;
        }

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=low; i<high; i++) {
            if (isPNum(i)) {
                list.add(i);
            }
        }
        if (list.isEmpty()) {
            System.out.println(0);
            return;
        }

        int first_pos = 0;
        int second_pos = 0;
        for (int i=0; i<list.size();i++) {
            int v = list.get(i) % 10;
            first_pos += v;

            v = (list.get(i)/10) % 10;
            second_pos += v;
        }
        if (first_pos < second_pos) {
            System.out.println(first_pos);
        } else {
            System.out.println(second_pos);
        }
    }

    private static boolean isPNum(int n) {
        boolean b = false;
        for (int i=2; i<n; i++) {
            if ((n%i) == 0) {
                b = true;
                break;
            }
        }
        return !b;
    }
}
