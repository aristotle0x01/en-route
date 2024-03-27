package sync.demo;

/**
 * A thread with delays.
 */

public class TestThread extends Thread {
    protected static void definiteDelay(int milisec ) {
        try { Thread.sleep(milisec); }
        catch( InterruptedException e ) { } }
    
    protected static void randomDelay(int maxMilisec ) {
        int x = (int)(Math.random() * maxMilisec ) ;
        definiteDelay( x ) ; }
}
