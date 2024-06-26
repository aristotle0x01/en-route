package sync.demo;

import sync.* ;

 public class FIFO extends AbstractMonitor {

	private final int capacity = 10 ;
	 
	private final Object[] queue = new Object[ capacity ] ;
	 
	private volatile int count = 0, front = 0 ;
	 
	/** Awaiting ensures: count < capacity */
	private final Condition notFull = makeCondition() ;
	 

	/** Awaiting ensures: count > 0 */
	private final Condition notEmpty = makeCondition() ;

	 
	public Object fetch() {
		enter() ;
			if( ! (count > 0 ) ) { notEmpty.await() ; assert count > 0 ; }
			Object value = queue[front ] ;
			count -- ;
			front = (front+1)%capacity ;
			assert count < capacity ; notFull.signal() ;
		leave() ;
		return value ; }
	 
	public void deposit( Object value ) {
		enter() ;
			if( ! ( count < capacity ) ) { notFull.await() ; assert count < capacity ; }
			queue[  (front + count) % capacity ] = value ;
			count ++ ;
			assert count > 0 ; notEmpty.signal() ;
		leave() ; }
	 
	@Override
	protected boolean invariant() {
		return 0 <= count && count <= capacity
			&& 0 <= front && front < capacity ; }

}