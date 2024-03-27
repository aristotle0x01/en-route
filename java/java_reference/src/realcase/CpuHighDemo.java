package realcase;// this is to demo a java instance cpu high

// https://dafengge0913.github.io/java-high-cpu-troubleshooting/

// /var/shared/openjdk/build/linux-x86_64-normal-server-slowdebug/jdk/bin/java
// /var/shared/openjdk/build/linux-x86_64-normal-server-slowdebug/jdk/bin/jstack 449 |grep -A 50 nid=0x1c2
public class CpuHighDemo {
    public static void main(String[] args) {
        int i = 1;
        while (true) {
            i = i*10;
        }
    }
}
