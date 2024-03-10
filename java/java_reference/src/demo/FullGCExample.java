package demo;

import java.util.ArrayList;
import java.util.List;

// /var/shared/openjdk/build/linux-x86_64-normal-server-slowdebug/jdk/bin/jstack 901 |grep -A 30 38e
// /var/shared/openjdk/build/linux-x86_64-normal-server-slowdebug/jdk/bin/jstat -gcutil 901 2000
// /var/shared/openjdk/build/linux-x86_64-normal-server-slowdebug/jdk/bin/jmap -dump:live,format=b,file=heap.bin 901
public class FullGCExample {
    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();

        while (true) {
            byte[] data = new byte[1000 * 1024]; // 创建100KB的字节数组
            list.add(data);

            if (list.size() >= 1000) {
                list.clear(); // 清空列表，触发Full GC
            }
        }
    }
}
