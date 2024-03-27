package sync.demo;

import sync.* ;

 public class FIFO1 extends AbstractMonitor {

	private final int capacity = 10 ;
	 
	private final Object[] queue = new Object[ capacity ] ;
	 
	private volatile int count = 0, front = 0 ;
	 
	/** Awaiting ensures: count < capacity */
	private final Condition notFull = makeCondition(
			new Assertion() {
				@Override public boolean isTrue() {
					return count < capacity ; } } ) ;
	 

	/** Awaiting ensures: count > 0 */
	private final Condition notEmpty = makeCondition(
			new Assertion() {
				@Override
				public boolean isTrue() {
					return count > 0 ; } } ) ;

	 
	public Object fetch() {
		enter() ;
			notEmpty.conditionalAwait() ;
			Object value = queue[front ] ;
			count -- ;
			front = (front+1)%capacity ;
			notFull.signal() ;
		leave() ;
		return value ; }
	 
	public void deposit( Object value ) {
		enter() ;
			notFull.conditionalAwait() ;
			queue[  (front + count) % capacity ] = value ;
			count++ ;
			notEmpty.signal() ;
		leave() ; }
	 
	@Override
	protected boolean invariant() {
		return 0 <= count && count <= capacity
			&& 0 <= front && front < capacity ; }

}