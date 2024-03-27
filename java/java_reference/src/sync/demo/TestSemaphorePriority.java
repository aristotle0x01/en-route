package sync.demo;
import sync.Semaphore;

public class TestSemaphorePriority {

	final static int WAITERS = 11 ;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Semaphore sem = new Semaphore(0) ;
		DoneCounter doneCounter = new DoneCounter() ;

		doneCounter.set( WAITERS ) ;
		for( int k=0 ; k < WAITERS ; ++k ) {
			Thread w = new PriorityWaiter( sem, doneCounter, k ) ;
			w.start(); 
			System.out.println("Started " + k ) ;
		}
		TestThread.definiteDelay( 2000 ) ;
		for( int k=0 ; k < WAITERS  ; ++k ) {
			sem.release() ;
			TestThread.definiteDelay( 100 ) ;
		}
		doneCounter.waitForDone() ;
		System.out.println( "Main done" );
	}
}

class PriorityWaiter extends TestThread {

	private Semaphore semaphore ;
	private DoneCounter doneCounter ;
	private int k ;

	PriorityWaiter( Semaphore sem , DoneCounter done, int k ) {
		semaphore = sem; 
		doneCounter = done ;
		this.k = k ; }

	public void run() {
		definiteDelay( 100 * k ) ;
		// 0    1    2    3    4    5    6    7    8    9   10
		// MAX  16   9    4    1    0    1    4    9    16  MAX
		// Hence order of release should be
		//  5 4 6 3 7 2 8 1 9 0 10
		if( k == 0 || k == TestSemaphorePriority.WAITERS-1 )
			semaphore.acquire() ;
		else
			semaphore.acquire((5-k)*(5-k)) ;
		int count = doneCounter.increment() ;
		System.out.println("Waiter " +k +". Done "+count) ; }
}
