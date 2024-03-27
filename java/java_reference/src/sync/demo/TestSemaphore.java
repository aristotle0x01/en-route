package sync.demo;
import sync.Semaphore;

public class TestSemaphore {

	final static int WAITERS = 100 ;
	final static int WAITS = 100 ;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Semaphore sem = new Semaphore(0) ;
		DoneCounter doneCounter = new DoneCounter() ;

		doneCounter.set( WAITERS ) ;
		System.out.println("Please wait. This takes a while");
		for( int k=0 ; k < WAITERS ; ++k ) {
			Thread w = new Waiter( sem, doneCounter ) ;
			w.start(); 
			System.out.println("Started " + w ) ;
		}
		for( int k=0 ; k < WAITERS * WAITS ; ++k ) {
			sem.release() ;
			System.out.println("Released " + (k+1) ) ;
		}
		doneCounter.waitForDone() ;
		System.out.println( "Main done" );
	}
}

class Waiter extends TestThread {

	private Semaphore semaphore ;
	private DoneCounter doneCounter ;

	Waiter( Semaphore sem , DoneCounter done ) {
		semaphore = sem; 
		doneCounter = done ; }

	public void run() {
		for(int i=0 ; i < TestSemaphore.WAITS ; ++i ) {

			semaphore.acquire() ;
		}
		int count = doneCounter.increment() ;
		System.out.println("Waiter " +Thread.currentThread() +" Done "+count) ; }
}
