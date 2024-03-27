package sync.demo;
import sync.Assertion;
import sync.Semaphore;

public class TestMutex {

	final static int THREADS = 100 ;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long time = System.currentTimeMillis() ;
		SharedResource resource = new SharedResource() ;
		DoneCounter doneCounter = new DoneCounter() ;
		Gate gate = new Gate() ;
		
		doneCounter.set( THREADS ) ;
		System.out.println("Please wait. This takes a while");
		for( int k=0 ; k < THREADS ; ++k ) {
			Thread w = new Competitor( resource, doneCounter, gate ) ;
			w.start(); 
			System.out.println("Started " + w ) ;
		}
		gate.go()  ;
		doneCounter.waitForDone() ;
		System.out.println( "Main done. Time was " + (System.currentTimeMillis()- time) );
	}
}

class Gate {
	private volatile boolean gate ;
	public void await() { while(!gate) Thread.yield() ; }
	public void go() { gate = true ; } 
}

class SharedResource {
	volatile int x = 0 ;
	Semaphore sem5 = new Semaphore( 5 ) ;
	Semaphore sem1 = new Semaphore( 1 ) ;
	
	public void useResource() {
		sem5.acquire() ;
		sem1.acquire() ; x = x + 1 ; sem1.release() ;
		Assertion.check(x <= 5, "Mutual exclusion failed" ) ;
		sem1.acquire() ; x = x - 1 ; sem1.release() ;
		sem5.release() ;
	}
}

class Competitor extends TestThread {

	private SharedResource resource ;
	private DoneCounter doneCounter ;
	private Gate gate ; 
	final int ITERATIONS = 100000 ;

	Competitor( SharedResource r , DoneCounter done, Gate g ) {
		resource = r; 
		doneCounter = done ;
		gate = g ;}

	public void run() {
		gate.await() ;
		for(int i=0 ; i < ITERATIONS ; ++i ) {
			resource.useResource() ;
		}
		int count = doneCounter.increment() ;
		System.out.println("Waiter " +Thread.currentThread() +" Done "+count) ; }
}
