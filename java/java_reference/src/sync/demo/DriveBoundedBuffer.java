package sync.demo;

/**
 * <p>Title: Monitor package and examples</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Memorial University of Newfoundland</p>
 * @author Theodore S. Norvell
 * @version 1.0
 */

/** Test a bounded buffer with multiple producers and consumers.
 */

public class DriveBoundedBuffer {

    public static void main(String[] args) {
        BoundedBufferInterface rw_controller = new BoundedBuffer5(4);
        DoneCounter doneCounter = new DoneCounter() ;

        final int CONSUMERS = 10 ;
        final int PRODUCERS = 10 ;
        doneCounter.set( CONSUMERS + PRODUCERS ) ;
        System.out.println("Please wait. This takes a while");
        for( int k=0 ; k < CONSUMERS ; ++k ) {
          Thread w = new Consumer( rw_controller, doneCounter ) ;
          w.start(); }
        for( int k=0 ; k < PRODUCERS ; ++k ) {
          Thread r = new Producer( rw_controller, doneCounter ) ;
          r.start(); }
        doneCounter.waitForDone() ;
        System.out.println( "Main done" );
    }
}

class Producer extends TestThread {

    private BoundedBufferInterface boundedBuffer ;
    private DoneCounter doneCounter ;

    Producer( BoundedBufferInterface bb, DoneCounter d ) {
        boundedBuffer = bb ; doneCounter = d ; }

    public void run() {
    	for(int i=0 ; i < 10000 ; ++i ) {
    		//delay(100) ;
    		
    		try {
    			boundedBuffer.deposit( (char)('a'+i) ) ; }
    		catch(InterruptedException e ) { }
    		//delay(100) ;
    	}
    	int count = doneCounter.increment() ;
    	System.out.println("Producer " +Thread.currentThread() +" Done "+count) ; }
}

class Consumer extends TestThread {

    private BoundedBufferInterface boundedBuffer ;
    private DoneCounter doneCounter ;

    Consumer( BoundedBufferInterface bb, DoneCounter d ) {
        boundedBuffer = bb ; doneCounter = d ; }

    public void run() {
        StringBuffer sb = new StringBuffer() ;

        for(int i=0 ; i < 10000 ; ++i ) {
          //delay(100) ;
          char ch ;
          try {
              ch = boundedBuffer.fetch() ; }
          catch(InterruptedException e ) { ch = '.' ; }
          sb.append( ch ) ;
          //delay(100) ;
        }
        doneCounter.increment() ;
        System.out.println("Consumer " +Thread.currentThread() +" <" + sb + ">" ) ; }
}