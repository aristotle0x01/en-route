package sync.demo;

// 题目求助｜字节跳动一面问题｜读取volatile变量会影响其他no-volatile变量在工作内存的值吗？
// https://leetcode.cn/circle/discuss/8X13Ub/?um_chnnl=huawei?um_from_appkey=5fcda41c42348b56d6f8e8d5
public class Volatile {

    boolean run = true;
    volatile int s = 1;

    public static void main(String[] args) throws InterruptedException {
        Volatile v = new Volatile();
        System.out.println("Namespace: " + v.getClass().getClassLoader());

        //thread 1
        new Thread(() ->{
            while (v.run) {
                int a = v.s;
                v.s = 2;
            }
        }).start();

        //thread 2
        new Thread(() ->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            v.run = false;
        }).start();

        System.out.println("exit!");
    }
}