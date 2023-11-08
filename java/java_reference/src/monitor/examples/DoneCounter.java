package examples;

import monitor.* ;

/** Count the number of threads that complete a task.
 * One thread can wait until they are all done.
 */

public class DoneCounter extends AbstractMonitor {
    int count = 0 ;
    int goal = 0 ;
    Condition done = makeCondition( new Assertion() {
        public boolean isTrue() { return count == goal ; } } ) ;

    public void set( int val ) {
        enter() ;
        goal = val ;
        count = 0 ;
        leave() ; }

    public int increment() {
        enter() ;
        count += 1 ;
        int value = count ;
        done.conditionalSignal() ;
        leave() ;
        return value ;
    }

    public void waitForDone() {
        enter() ;
        done.conditionalAwait() ;
        leave() ;
    }
}