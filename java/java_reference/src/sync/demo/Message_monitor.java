package sync.demo;

import sync.* ;

/** Store a single message */
public class Message_monitor extends AbstractMonitor
{
    private Object data_object = null ;
    
    // The following two classes represent propositions that
    // are checked out of paranoia.
    class isFull extends Assertion {
        public boolean isTrue() { return data_object != null ; } }
    class isEmpty extends Assertion {
        public boolean isTrue() { return data_object == null ; } }
        
    private Condition full = makeCondition( new isFull() ) ;
    private Condition empty = makeCondition( new isEmpty() ) ;
    
    /** Wait until there is no Object in the monitor
      Then place an object in the monitor.
    */
    public void put( Object o ) {
        try {
            enter() ;
            empty.conditionalAwait() ;
            data_object = o ; 
            full.signal() ; }
        finally {
            leave() ; }
    }
    
    /** Is there an object in the monitor?
        @return null if not returns the object if there is
                 (removing the object).
    */
    public Object poll() {
        Object rtn ;
        try {
            enter() ;
            rtn = data_object ;
            data_object = null ;
            if( rtn != null ) empty.signal() ; }
        finally {
            leave() ; }
        return rtn ; }
   
   /* Probe the monitor to see if there is a message
      @returns true if there is an object in the monitor
   */
   public boolean probe() {
        boolean rtn ;
        try {
            enter() ;
            rtn = data_object != null ; }
        finally {
            leave() ; }
        return rtn ;
   }
   
   /** Get an object from the monitor.
       Waits until there is an object the monitor,
       then removes it and returns it.
   */
   public Object get() {
        Object rtn ;
        try {
            enter() ;
            full.conditionalAwait() ;
            rtn = data_object ;
            data_object = null ;
            empty.signal() ; }
        finally {
            leave() ; }
        return rtn ; }
}