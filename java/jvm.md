## jvm

[Understanding JVM Internals](https://archive.is/no6uZ) 从jvm角度讲线程栈结构和执行

[jvm internals](https://blog.jamesdbloom.com/JVMInternals.html) from jvm

[Dissecting the Java Virtual Machine - Architecture - part 3](https://martin-toshev.com/index.php/software-engineering/architectures/80-dissecting-the-java-virtual-machine)



Q: classloader为什么要进行namespace隔离？

A: 

> By allowing you to instantiate user-defined class loaders that know how to download class files across a network, Java's class loader architecture supports network-mobility. It supports security by allowing you to load class files from different sources through different user-defined class loaders. This puts the class files from different sources into different name-spaces, which allows you to restrict or prevent access between code loaded from different sources.



## class file format

[Chapter 4. The `class` File Format](https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7)

![class file format](https://user-images.githubusercontent.com/2216435/285096072-36305922-a3c5-40ba-903d-dd31c7dd091c.png)



## java线程

[Java Concurrency and Multithreading Tutorial](http://tutorials.jenkov.com/java-concurrency/index.html) 非常完整详细

[Thread, code and data - Story of a Multithreading Program in Java](https://javarevisited.blogspot.com/2019/02/thread-code-and-data-how-multithreading-java-program-execute.html) 从main 方法开始



## jvm图示

|      | <img src="https://github.com/aristotle0x01/en-route/assets/2216435/cd228cba-105d-4622-b42b-c3c2b6b908f7" alt="put/get" style="zoom:70%; float: left;" /> |
| ---- | ------------------------------------------------------------ |
|      | <img src="https://github.com/aristotle0x01/en-route/assets/2216435/9e485ec9-ab0e-4667-8a3c-aedefc71cac1" alt="put/get" style="zoom:90%; float: left;" /> |
|      | <img src="https://github.com/aristotle0x01/en-route/assets/2216435/584083ca-7751-4d9e-b87b-c3b4c9b60e94" alt="put/get" style="zoom:90%; float: left;" /> |
|      |                                                              |
|      | <img src="https://github.com/aristotle0x01/en-route/assets/2216435/542dc011-e705-4bcc-af0e-268946abe77a" alt="put/get" style="zoom:90%; float: left;" /> |
|      |                                                              |
|      | <img src="https://github.com/aristotle0x01/en-route/assets/2216435/edc2651a-9650-4d26-a944-1ae1675dc038" alt="put/get" style="zoom:90%; float: left;" /> |
|      | <img src="https://github.com/aristotle0x01/en-route/assets/2216435/7ce5d531-3cfa-4a5e-b714-cca0632450ab" alt="put/get" style="zoom:90%; float: left;" /> |

