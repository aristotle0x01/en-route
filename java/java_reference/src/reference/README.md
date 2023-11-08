# java_reference
on java soft, weak and phantom reference

java引用类层次
![image](https://user-images.githubusercontent.com/2216435/76060126-8bf78900-5fbb-11ea-9e44-858f849e549d.png)

## Reference Queue
ReferenceQueue:

	Reference queues, to which registered reference objects are appended by the garbage collector after the appropriate reachability changes are detected.

What does a reachability change mean ?

We know that the Garbage collector will free up any weak reference objects or weakly reachable objects as a part of the garbage collection process. The flow for the same is:
If the garbage collector discovers an object that is weakly reachable, the following occurs:

1. The WeakReference object's referent field is set to null, thereby making it not refer to the heap object any longer. 
2. The heap object that had been referenced by the WeakReference is declared finalizable. 
3. The WeakReference object is added to its ReferenceQueue. Then the heap object's finalize() method is run and its memory freed. 

So what is this ReferenceQueue ?
When creating non-strong reference objects we have the option of passing a reference queue as a part of the Reference constructor. As seen from the above explanation, this reference queue is a way for the GC to inform the program that a certain object is no longer reachable.

[Reference Queues
](http://learningviacode.blogspot.com/2014/02/reference-queues.html)

## Reference Objects and Garbage Collection
[Reference Objects and Garbage Collection](http://pawlan.com/monica/articles/refobjs/)

非常好的文章，从垃圾收集与各种引用的关系来说明，非常清晰透彻

java gc的可达性说明

![image](https://user-images.githubusercontent.com/2216435/76060027-52268280-5fbb-11ea-8542-da569a41efb0.png)

弱引用说明

![image](https://user-images.githubusercontent.com/2216435/76060080-6ff3e780-5fbb-11ea-8a17-ddd6fc671b3b.png)

强弱共存

![image](https://user-images.githubusercontent.com/2216435/76060110-81d58a80-5fbb-11ea-9edf-26f473623181.png)



## Phantom Reference
[Java Garbage Collection - Understanding Phantom Reference with examples](https://www.logicbig.com/tutorials/core-java-tutorial/gc/phantom-reference.html)

[Features of PhantomReference](https://codegym.cc/groups/posts/213-features-of-phantomreference)
