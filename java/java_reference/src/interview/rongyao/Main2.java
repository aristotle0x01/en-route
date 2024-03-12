package interview.rongyao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main2 {
    // max: 23:59:59
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int[] input = new int[6];
        while (in.hasNextLine()) {
            StringBuilder sb = new StringBuilder(in.next());
            sb.deleteCharAt(0);
            sb.deleteCharAt(sb.length()-1);
            String[] sa = sb.toString().split(",");
            for (int i=0; i<6; i++) {
                input[i] = Integer.parseInt(sa[i]);
            }
            break;
        }

//        while (in.hasNextInt()) { // 注意 while 处理多个 case
//            input[0] = in.nextInt();
//            input[1] = in.nextInt();
//            input[2] = in.nextInt();
//            input[3] = in.nextInt();
//            input[4] = in.nextInt();
//            input[5] = in.nextInt();
//            break;
//        }
        for (int i=0; i<6; i++) {
            if (input[i] < 0 || input[i] > 9) {
                System.out.println("invalid");
                return;
            }
        }

        // hour:
        // 0: 0 - 9
        // 1: 0 - 9
        // 2: 0 - 3
        for (int h1=2; h1>=0; h1--) {
            StringBuilder sb = new StringBuilder();
            ArrayList<Integer> list = new ArrayList<>(Arrays.asList(input[0], input[1], input[2], input[3], input[4], input[5]));
            int i = list.indexOf(h1);
            if (i == -1) {
                continue;
            }
            sb.append(h1);
            list.remove(i);

            for (int h2=9; h2>=0; h2--) {
                if (h1==2 && h2>=4) {
                    continue;
                }

                i = list.indexOf(h2);
                if (i == -1) {
                    continue;
                }
                sb.append(h2);
                list.remove(i);
                sb.append(":");

                // minute:
                // 0: 0 - 9
                // 1: 0 - 9
                // ...
                // 5: 0 - 9
                for (int m1=5; m1>=0;m1--) {
                    i = list.indexOf(m1);
                    if (i == -1) {
                        continue;
                    }
                    sb.append(m1);
                    list.remove(i);

                    for (int m2=9; m2>=0;m2--) {
                        i = list.indexOf(m2);
                        if (i == -1) {
                            continue;
                        }
                        sb.append(m2);
                        list.remove(i);
                        sb.append(":");

                        // second:
                        // 0: 0 - 9
                        // 1: 0 - 9
                        // .......
                        // 5: 0 - 9
                        for (int s1=5; s1>=0; s1--) {
                            i = list.indexOf(s1);
                            if (i == -1) {
                                continue;
                            }
                            sb.append(s1);
                            list.remove(i);

                            for (int s2=9; s2>=0; s2--) {
                                i = list.indexOf(s2);
                                if (i == -1) {
                                    continue;
                                }
                                sb.append(s2);
                                list.remove(i);
                                System.out.println(sb.toString());
                                return;
                            }
                        }
                    }
                }
            }
        }

        // not found valid pattern
        System.out.println("invalid");
    }
}
