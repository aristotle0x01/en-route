package examples;

import monitor.*;

/**
 * <p>Title: Monitor package and examples</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Memorial University of Newfoundland</p>
 * @author Theodore S. Norvell
 * @version 1.0
 */

/** BoundedBuffer using a Monitor object. */

class BoundedBuffer3 implements BoundedBufferInterface {
	private char buf[];
	private int front, rear, count, n ;
	private Monitor monitor = new Monitor(
			// Invariant
			new Assertion() {
				public boolean isTrue() { return 0 <= count && count <= n ; } } ) ;
	private Condition notFull = monitor.makeCondition(
			new Assertion() {
				public boolean isTrue() { return count<n ; } } ) ;
	private Condition notEmpty = monitor.makeCondition(
			new Assertion() {
				public boolean isTrue() { return count>0 ; } } ) ;

	public BoundedBuffer3(int n) {
		this.n = n ;
		buf = new char[n] ;
		front = rear = count = 0 ;
	}

	public void deposit(char data)
	{
		monitor.enter() ;
		notFull.conditionalAwait();
		buf[rear] = data;
		rear = (rear+1)%n;
		count++;
		notEmpty.signal();
		monitor.leave() ;
	}

	public char fetch()
	{
		monitor.enter() ;
		notEmpty.conditionalAwait();
		char result = buf[front];
		front = (front+1)%n;
		count--;
		notFull.signal();
		monitor.leave() ;
		return result;
	}
}