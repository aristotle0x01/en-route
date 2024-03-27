package sync.demo;

/** BoundedBuffer1 Implement BoundedBuffer using notify and wait.
 * 
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Memorial University of Newfoundland</p>
 * @author Theodore S. Norvell
 * @version 1.0
 */

class BoundedBuffer1 implements BoundedBufferInterface {
	private char buf[];
	private int front, rear, count, n;

	public BoundedBuffer1(int n) {
		this.n = n ;
		buf = new char[n] ;
		front = rear = count = 0 ;
	}
	
	public synchronized void deposit(char data)
	throws InterruptedException
	{
		while (count == n) wait();
		buf[rear] = data;
		rear = (rear+1)%n;
		count++;
		notifyAll();
	}
	
	public synchronized char fetch()
	throws InterruptedException
	{
		while (count == 0) wait();
		char result = buf[front];
		front = (front+1)%n;
		count--;
		notifyAll();
		return result;
	}
}
