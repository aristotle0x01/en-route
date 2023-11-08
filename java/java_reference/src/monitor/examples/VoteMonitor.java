package examples;

import monitor.*;

/**
 * <p>Title: Monitor package and examples</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Memorial University of Newfoundland</p>
 * @author Theodore S. Norvell
 * @version 1.0
 */

public class VoteMonitor extends AbstractMonitor implements VoteInterface {

    private final int N ;
    private int votesFor = 0, votesAgainst = 0 ;

    private Condition electionDone = makeCondition( new Assertion() {
        @Override public boolean isTrue() {
        	return votesFor+votesAgainst == N ; } } ) ;

    public VoteMonitor(int N) {
    	assert N > 0 ;
        this.N = N ; }

    @Override protected boolean invariant() {
        return 0 <= votesFor && 0 <= votesAgainst
            && votesFor+votesAgainst < N  ; }

    public boolean castVoteAndWaitForResult(boolean vote) {
        enter() ;

	        if( vote ) votesFor++ ; else votesAgainst++ ;
	
	        electionDone.conditionalAwait() ;
	
	        // Assert: votesFor+votesAgainst == N
	        boolean result = votesFor > votesAgainst ;
	
	        if( ! electionDone.isEmpty() ) {
	            electionDone.signalAndLeave() ; }
	        else {
	            // The last one woken up is the first one out
	            // and cleans up.
	            votesFor = votesAgainst = 0 ;
	            leave() ;}

        return result ; }
}