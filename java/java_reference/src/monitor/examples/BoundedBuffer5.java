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

/** A BoundedBuffer which extends AbstractMonitor. */

class BoundedBuffer5 extends AbstractMonitor implements BoundedBufferInterface {
	private char buf[];
	private int front, rear, count, n ;

	@Override public boolean invariant() {
		return 0 <= count && count <= n ;
	}
	private Condition notFull = makeCondition(
			new Assertion() {
				public boolean isTrue() { return count<n ; } } ) ;

	private Condition notEmpty = makeCondition(
			new Assertion() {
				public boolean isTrue() { return count>0 ; } } ) ;

	public BoundedBuffer5(int n) {
		this.n = n ;
		buf = new char[n] ;
		front = rear = count = 0 ;
	}

	public void deposit(final char data)
	{
		doWithin( new Runnable() {
			public void run() {
				notFull.conditionalAwait();
				buf[rear] = data;
				rear = (rear+1)%n;
				count++;
				notEmpty.signal();
			}} ) ;
	}

	public char fetch()
	{  
		return doWithin( new RunnableWithResult<Character>() {
			public Character run() {
				notEmpty.conditionalAwait();
				char result = buf[front];
				front = (front+1)%n;
				count--;
				notFull.signal();
				return result;
			}} ) ;
	}
}