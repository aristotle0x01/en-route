package sync.demo;

import sync.*;

/** Mediate a rendezvous between a server and a client.
 *
 * <p>Title: Monitor package and examples</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Memorial University of Newfoundland</p>
 * @author Theodore S. Norvell
 * @version 1.0
 */

 public class Rendezvous extends AbstractMonitor
{
    private Object request = null ;
    private Object reply = null ;
    /** States are encoded as follows:
     *  state==0. No request recieved. Clients may leave request
     *  state==1. Request recieved by monitor. Server may collect request.
     *  state==2. Request recieved by a server. Server is acting on request
     *  state==3. Request acted on. Reply recieved by monitor. Server has
     *            completed the transaction. Client may collect reply.
     *
     *     Client leaves request    Server collects request
     *   0 ---------------------> 1 -------------------------> 2
     *   ^                                                     |
     *   |  Client collects reply       Server leaves reply    |
     *   +------------------------- 3 <------------------------+
     *
     */
    private int state ;

    private Condition readyToStart
    = makeCondition( new Assertion() {
        public boolean isTrue() { return state==0 ; } } ) ;

    private Condition requestIsMade
    = makeCondition( new Assertion() {
        public boolean isTrue() { return state==1 ; } } ) ;

    private Condition replyIsReady
    = makeCondition( new Assertion() {
        public boolean isTrue() { return state==3 ; } } ) ;

    protected boolean invariant() {
        return      request==null && reply==null && state==0
                 || request!=null && reply==null && state==1
                 || request!=null && reply==null && state==2
                 || request!=null && reply!=null && state==3 ; }

    /** Submit a request and collect the reply.
     *  To be called by the client.
    */
    public Object submitRequest( Object request ) {
        enter() ;

        readyToStart.conditionalAwait();
        this.request = request ;
        state = 1 ;
        requestIsMade.signal();
        replyIsReady.conditionalAwait();
        Object reply = this.reply ;

        this.reply = null ;
        this.request = null ;
        state = 0 ;
        readyToStart.signal() ;

        leave() ;
        return reply ;
    }

    /** Get a request.
     *  To be called by a server. Grants exclusive right to call setReply */
    public Object getRequest() {
        enter() ;

        requestIsMade.conditionalAwait();
        Object request = this.request ;
        state = 2 ;

        leave() ;
        return request ;
    }

    /** Send back a reply
     *  To be called by a server.
     */
    public void setReply( Object reply ) {
        enter() ;

        this.reply = reply ;
        state = 3 ;
        replyIsReady.signalAndLeave() ;
    }
}