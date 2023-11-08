package examples;

/** A failed attempt to simulate condition variables in Java.
 * NOTE: This "solution" is not a solution at all.
 * It causes deadlock because when a thread waits on notFull or on notEmpty,
 * it does not unlock the BoundedBuffer2 object.
 * <p>Title: Monitor package and examples</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Memorial University of Newfoundland</p>
 * @author Theodore S. Norvell
 * @version 1.0
 */

class BoundedBuffer2 implements BoundedBufferInterface {
	private char buf[];
	private int front, rear, count, n ;
	private Object notFull = new Object() ;
	private Object notEmpty = new Object() ;

	public BoundedBuffer2(int n) {
		this.n = n ;
		buf = new char[n] ;
		front = rear = count = 0 ;
	}
	
	public synchronized void deposit(char data)
	throws InterruptedException
	{
		while (count == n)
			synchronized( notFull ) { notFull.wait(); } ;
			buf[rear] = data;
			rear = (rear+1)%n;
			count++;
			synchronized( notEmpty ) { notEmpty.notifyAll(); }
	}
	
	public synchronized char fetch()
	throws InterruptedException
	{
		while (count == 0) synchronized( notEmpty ) { notEmpty.wait(); }
		char result = buf[front];
		front = (front+1)%n;
		count--;
		synchronized( notFull ) { notFull.notifyAll(); }
		return result;
	}
}
