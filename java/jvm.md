## jvm

[Understanding JVM Internals](https://www.cubrid.org/blog/understanding-jvm-internals/) 从jvm角度讲线程栈结构和执行

[jvm internals](https://blog.jamesdbloom.com/JVMInternals.html) from jvm



Q: classloader为什么要进行namespace隔离？

A: 

> By allowing you to instantiate user-defined class loaders that know how to download class files across a network, Java's class loader architecture supports network-mobility. It supports security by allowing you to load class files from different sources through different user-defined class loaders. This puts the class files from different sources into different name-spaces, which allows you to restrict or prevent access between code loaded from different sources.



## java线程

[Java Concurrency and Multithreading Tutorial](http://tutorials.jenkov.com/java-concurrency/index.html) 非常完整详细

[Thread, code and data - Story of a Multithreading Program in Java](https://javarevisited.blogspot.com/2019/02/thread-code-and-data-how-multithreading-java-program-execute.html) 从main 方法开始



