package examples;

import monitor.* ;

public class FIFOQueue1 extends AbstractMonitor {
	  private Object buf[];
	  private int front, rear, count, n ;
	  private Condition notFull = makeCondition(
	        new Assertion() {
	            public boolean isTrue() { return count<n ; } } ) ;
	  private Condition notEmpty = makeCondition(
	        new Assertion() {
	            public boolean isTrue() { return count>0 ; } } ) ;

	  public FIFOQueue1(int n) {
	    Assertion.check( n > 0 ) ;
	    this.n = n ;
	    buf = new Object[n] ;
	    front = rear = count = 0 ;
	  }
	  
	  protected boolean invariant() {
		  return 0 <= count && count <= n
	          && 0 <= front && front < n
	          && (front+count)%n == rear ; }

	  public void deposit(Object data)
	  {
	    enter() ;
	    notFull.conditionalAwait();
	    buf[rear] = data;
	    rear = (rear+1)%n;
	    count++;
	    notEmpty.signal();
	    leave() ;
	  }

	  public Object fetch()
	  {
	    enter() ;
	    notEmpty.conditionalAwait();
	    Object result = buf[front];
	    front = (front+1)%n;
	    count--;
	    notFull.signal();
	    leave() ;
	    return result;
	  }
}
