## Q&A

**HashMap**

**Q**: HashTable, HashMap, ConcurrentHashMap对于null支持问题？

A: 设计问题，避免在并发情况下get歧义。支持null带来的代码复杂性使得在并发情况下支持null的价值不大

[Why Hashtable does not allow null keys or values?](https://stackoverflow.com/questions/11981852/why-hashtable-does-not-allow-null-keys-or-values)

```
// 实现问题不足虑也，重点是思考设计的出发点
if (map.contains(key)) {
    return map.get(key);
} else {
    throw new KeyNotFoundException;
}
```

// 0 & null 处于同一个bin

```
// hashmap
HashMap<Integer, String> map1 = new HashMap<>();
Integer i0 = 0;
int dd =  i0.hashCode();
map1.put(null, "cc");
map1.put(i0, "dd");
```

**Q**: ConcurrentHashMap怎么支持无锁读并发？

A: 

**Q**: `synchronized (f) {  if (tabAt(tab, i) == f)...` 在变更操作中的作用？

A: 

**Q**: ConcurrentHashMap get(key)同时发生resize()，如何保证get？

A: 

------

java的三种锁实现

**Q**: 为什么要有biased/light lock？

A: 避免操作系统调用及休眠，乃至于cas

- 偏向锁：无竞争的情况下，只有一个线程进入临界区，采用偏向锁
- 轻量级锁：多个线程可以交替进入临界区，采用轻量级锁
- 重量级锁：多线程同时进入临界区，交给操作系统互斥量来处理

[难搞的偏向锁终于被 Java 移除了](https://segmentfault.com/a/1190000041194920) <u>*(本地有备份)*</u>

[The Hotspot Java Virtual Machine](https://www.cs.princeton.edu/picasso/mats/HotspotOverview.pdf)

------

**locking**

**Q**: ostep 中的经典生产消费者问题，下述实现存在的两个重要bug？

| <img src="https://user-images.githubusercontent.com/2216435/277291077-fabceceb-5f88-4bce-8b5b-955b08c487c2.png" alt="put/get" style="zoom:30%; float: left;" /> | <img src="https://user-images.githubusercontent.com/2216435/277291821-f711c075-6d81-40a1-975f-76c52fd93f52.png" alt="put/get" style="zoom:50%; float: left;" /> |
| --------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------- |

A: 

1)c2/p2 **if** 条件判断问题：如果生产者唤醒一个消费者X1(runnable)，而另外一个消费者X2本来已处于runnable状态并被首先调度完成消费，那么X1开始执行时count又重新为零，状态已不符。(Mesa semantics VS Hoare semantics)

> Signaling a thread only wakes them up; it is thus a hint that the state of the world has changed (in this case, that a value has been placed in the buffer), but there is no guarantee that when the woken thread runs, the state will still be as desired.

2)多消费者情况下，如果c5(代码行)唤醒了另一个消费者，那么生产者将永远休眠

[ostep: Condition Variables](https://pages.cs.wisc.edu/~remzi/OSTEP/threads-cv.pdf)

**note**: pthread_cond_wait特点，原子性释放锁并进入休眠

1）xv6-public中`sleeplock.c`之`acquiresleep`方法

2）java中`Condition.await`类同，详见`ProducerConsumerDemo`

**Q**: 锁的几种实现？

A: 参考[ostep: Locks](https://pages.cs.wisc.edu/~remzi/OSTEP/threads-locks.pdf)

1)中断禁止-不可用

2)单变量-不可用

3)spinlock-性能

4)spin+yield-性能，公平性差

5)休眠+队列

6)休眠+队列+spin

| solaris lock                                                                                                                                                 | linux lock                                                                                                                                                    |
| ------------------------------------------------------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| <img src="https://user-images.githubusercontent.com/2216435/277613454-636a405a-63eb-4634-aec1-01a9937db866.png" alt="park" style="zoom:50%; float: left;" /> | <img src="https://user-images.githubusercontent.com/2216435/277614140-8a2890b8-2135-4a01-a1b6-62441ded5e68.png" alt="futex" style="zoom:50%; float: left;" /> |

实现：`6.828 hw/sync.c, java_reference/ProducerConsumerDemo.java`

------

**cache coherence & consistency**

- coherence: 强调多核cache之间的协调动作
- consistency：整体对外承诺的标准

**Q**: 什么是sequential consistency?

A: 如果某个核执行了`a=1;b=1`，那么其它核对于a,b的可见性能力上也要可以达到a=1先于b=1

[Sequential consistency in newbie terms?](https://stackoverflow.com/questions/54493739/sequential-consistency-in-newbie-terms) 

[How does Java do it? Motivation for C++ programmers](https://bartoszmilewski.com/2008/11/11/who-ordered-sequential-consistency/)

<u>Memory Barriers: a Hardware View for Software Hackers</u> 一文中3.3/4.3小节中增加memory barrier即为确保这种顺序，以保证程序正确性

<img src="https://user-images.githubusercontent.com/2216435/278514361-4540a1e8-0546-411b-9fe7-c78c52db1adf.png" alt="sequential consistency" style="zoom:35%; float: left;" />

**Q**: **MESI**协议为什么要多出来Exclusive这个状态?

A: 初始只有**MSI**三个状态

* 读时若未cache，则置于Exclusive状态
* 此后若再变更，则不必invalidate
* 可以认为大部分情况下，应该是单核操作数据，那么就减少了大量内核间通信

<img src="https://user-images.githubusercontent.com/2216435/281074573-fa3a2dac-b656-4cab-9cf5-2fd042e21fb0.png" alt="The MESI protocol" style="zoom:40%; float: left;" />

实际上在此基础上还有**false sharing, directory cache protocol**概念

**ref:**

**Memory Barriers: a Hardware View for Software Hackers**： [Cache Memory](https://homepage.divms.uiowa.edu/~ghosh/6016.8.pdf)有助于理解前文

[The MESI protocol](https://people.cs.pitt.edu/~melhem/courses/2410p/ch5-4.pdf): 解释了为什么需要Exclusive状态

[Lecture 17: Multi-Processors - Cache Coherency](https://inst.eecs.berkeley.edu/~cs152/sp22/lectures/L17-Coherence.pdf): MSI, directory cache protocol

[A Guide to False Sharing and @Contended](https://www.baeldung.com/java-false-sharing-contended#false-sharing): false sharing及其在java中的解决

[Memory Ordering at Compile Time](https://preshing.com/20120625/memory-ordering-at-compile-time/)

[Memory Consistency Models: A Tutorial](https://www.cs.utexas.edu/~bornholt/post/memory-models.html)

**java memory model**

[Doug Lea: The JSR-133 Cookbook for Compiler Writers](https://gee.cs.oswego.edu/dl/jmm/cookbook.html)

[The Java Memory Model∗](https://www.cs.tufts.edu/comp/150FP/archive/bill-pugh/java.pdf)

------

**Q**: 什么是premature publishing?

A: [Initializing non-final field](https://stackoverflow.com/questions/31223219/initializing-non-final-field)。new 一个新对象并赋值给一个引用后，对象的非final字段有可能尚未完成赋值。

------

**volatile**

**Q**: 字节跳动一面问题｜读取volatile变量会影响其他no-volatile变量在工作内存的值吗？

A: https://leetcode.cn/circle/discuss/8X13Ub/

[Guide to the Volatile Keyword in Java](https://www.baeldung.com/java-volatile)

[Java Volatile Keyword](https://jenkov.com/tutorials/java-concurrency/volatile.html#full-volatile-visibility-guarantee)

[Java theory and practice: Fixing the Java Memory Model, Part 2](https://archive.ph/pHqcD#selection-333.0-340.0)

**Q**: singleton double checking？

A: [The "Double-Checked Locking is Broken" Declaration](https://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html)

------

**java dynamic proxy**

Q: what is java proxy?

A: [Proxying Mechanisms](https://docs.spring.io/spring-framework/reference/core/aop/proxying.html#aop-understanding-aop-proxies)

* self invocation
* final method issue
* performance: cglib vs jdk
* bean加载循环依赖问题
* dubbo远程调用
