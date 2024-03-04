package interview.huawei;

import java.util.Scanner;

/*
在一款虚拟游戏中生活，你必须进行投资以增强在虚拟游戏中的资产以免被淘汰出局。现有一家Bank，它提供有若干理财产品m，风险及投资回报不同，你有N（元）进行投资，能接受的总风险值为X。
你要在可接受范围内选择最优的投资方式获得最大回报。
说明：
在虚拟游戏中，每项投资风险值相加为总风险值；
在虚拟游戏中，最多只能投资2个理财产品；
在虚拟游戏中，最小单位为整数，不能拆分为小数；
投资额*回报率=投资回报
第一行：产品数(取值范围[1, 20])，总投资额(整数，取值范围[1, 10000])，可接受的总风险(整数，取值范围[1, 200])
第二行：产品投资回报率序列，输入为整数，取值范围[1,60]
第三行：产品风险值序列，输入为整数，取值范围[1,100]
第四行：最大投资额度序列，输入为整数，取值范围[1,10000]
在虚拟游戏中，每项投资风险值相加为总风险值；
在虚拟游戏中，最多只能投资2个理财产品；
在虚拟游戏中，最小单位为整数，不能拆分为小数；
投资额*回报率=投资回报

5 100 10
10 20 30 40 50
3 4 5 6 10
20 30 20 40 30
*/
// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int numOfProducts = 0;
        int maxInvestMoney = 0;
        int maxRiskMoney = 0;
        int[] investReturnArray = null;
        int[] investRiskArray = null;
        int[] maxAllowedPerProductArray = null;
        int[] result = null;

        while (in.hasNextInt()) { // 注意 while 处理多个 case
            numOfProducts = in.nextInt();
            maxInvestMoney = in.nextInt();
            maxRiskMoney = in.nextInt();
            if (numOfProducts <= 0) {
                break;
            }

            investReturnArray = new int[numOfProducts];
            investRiskArray = new int[numOfProducts];
            maxAllowedPerProductArray = new int[numOfProducts];

            int k = 0;
            while (k < numOfProducts && in.hasNextInt()) {
                investReturnArray[k++] = in.nextInt();
            }
            k = 0;
            while (k < numOfProducts && in.hasNextInt()) {
                investRiskArray[k++] = in.nextInt();
            }
            k = 0;
            while (k < numOfProducts && in.hasNextInt()) {
                maxAllowedPerProductArray[k++] = in.nextInt();
            }
            break;
        }

        int maxReturn = 0;
        for (int i=0; i<numOfProducts; i++) {
            if (investRiskArray[i] > maxRiskMoney) {
                continue;
            }
            int amount = maxAllowedPerProductArray[i];
            if (amount > maxInvestMoney) {
                amount = maxInvestMoney;
            }
            int i_max_return = amount * investReturnArray[i];
            if (i_max_return > maxReturn) {
                maxReturn = i_max_return;

                result = new int[numOfProducts];
                result[i] = amount;
            }

            for (int j=i+1; j<numOfProducts; j++) {
                // 超过风险
                if ((investRiskArray[i]+investRiskArray[j]) > maxRiskMoney) {
                    continue;
                }

                if (investReturnArray[j] > investReturnArray[i]) {
                    if (maxAllowedPerProductArray[j] > maxInvestMoney) {
                        i_max_return = 1 * investReturnArray[i] + (maxInvestMoney-1) * investReturnArray[j];
                        if (i_max_return > maxReturn) {
                            maxReturn = i_max_return;

                            result = new int[numOfProducts];
                            result[i] = 1;
                            result[j] = maxInvestMoney-1;
                        }
                    } else {
                        int left_amount = maxInvestMoney - maxAllowedPerProductArray[j];
                        if (left_amount > maxAllowedPerProductArray[i]) {
                            left_amount = maxAllowedPerProductArray[i];
                        }
                        i_max_return = left_amount * investReturnArray[i] + maxAllowedPerProductArray[j] * investReturnArray[j];
                        if (i_max_return > maxReturn) {
                            maxReturn = i_max_return;

                            result = new int[numOfProducts];
                            result[i] = left_amount;
                            result[j] = maxAllowedPerProductArray[j];
                        }
                    }
                } else {
                    int left_amount = maxInvestMoney - amount;
                    if (left_amount <= 0) {
                        continue;
                    }
                    int jamount = left_amount;
                    if (jamount > maxAllowedPerProductArray[j]) {
                        jamount = maxAllowedPerProductArray[j];
                    }

                    i_max_return = amount * investReturnArray[i] + jamount * investReturnArray[j];
                    if (i_max_return > maxReturn) {
                        maxReturn = i_max_return;

                        result = new int[numOfProducts];
                        result[i] = amount;
                        result[j] = jamount;
                    }
                }
            }
        }

        if (result != null) {
            for (int i=0; i<numOfProducts; i++) {
                System.out.print(result[i]);
                System.out.print(" ");
            }
        }
        System.out.println("");
    }
}