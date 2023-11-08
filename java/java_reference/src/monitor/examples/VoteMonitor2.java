package examples;

import monitor.* ;

/**
 * <p>Title: Monitor package and examples</p>
 * <p>Description: This one uses two conditions.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Memorial University of Newfoundland</p>
 * @author Theodore S. Norvell
 * @version 1.0
 */

public class VoteMonitor2 extends AbstractMonitor implements VoteInterface {

    private int N ;
    private int votedFor = 0, votedAgainst = 0 ;

    private Condition electionDone = makeCondition( new Assertion() {
        public boolean isTrue() { return votedFor+votedAgainst == N ; } } ) ;

    private Condition electionOn = makeCondition( new Assertion() {
        public boolean isTrue() { return votedFor+votedAgainst < N ; } } ) ;

    public VoteMonitor2(int N) {
        this.N = N ; }


    protected boolean invariant() {
        return 0 <= votedFor && 0 <= votedAgainst
            && votedFor+votedAgainst <= N  ; }

    public boolean castVoteAndWaitForResult(boolean vote) {
        enter() ;

        electionOn.conditionalAwait() ;

        if( vote ) votedFor++ ; else votedAgainst++ ;

        if( votedFor+votedAgainst < N )
            electionOn.signal() ;

        if( votedFor+votedAgainst < N )
            electionDone.await() ;

        // Assert: votedFor+voetdAgainst == N
        boolean result = votedFor > votedAgainst ;

        if( ! electionDone.isEmpty() ) {
            electionDone.signal() ; }
        else {
            // The last one woken up is the first one out
            // and cleans up.
            votedFor = votedAgainst = 0 ;
            electionOn.signal() ;
        }

        leave() ;

        return result ; }
}