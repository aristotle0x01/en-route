package sync.demo;

import sync.* ;

public class FIFOQueue extends AbstractMonitor {
	  private Object buf[];
	  private volatile int front, rear, count;
	  private final int capacity ;
	  /** Signalled when count < capacity */
	  private final Condition notFull = makeCondition() ;
	  /** Signalled when count > 0 */
	  private final Condition notEmpty = makeCondition() ;

	  public FIFOQueue(int capacity) {
		Assertion.check( capacity > 0 ) ;
	    this.capacity = capacity ;
	    buf = new Object[capacity] ;
	    front = rear = count = 0 ;
	  }
	  
	  protected boolean invariant() {
		  return 0 <= count && count <= capacity
		      && 0 <= front && front < capacity
		      && (front+count)%capacity == rear ; }

	  public void deposit(Object data) {
	    enter() ;
	    if( count==capacity ) notFull.await();
	    buf[rear] = data;
	    rear = (rear+1)%capacity;
	    count++;
	    notEmpty.signal();
	    leave() ;
	  }

	  public Object fetch() {
	    enter() ;
	    if( count==0 ) notEmpty.await();
	    Object result = buf[front];
	    front = (front+1)%capacity;
	    count--;
	    notFull.signal();
	    leave() ;
	    return result;
	  }
}
