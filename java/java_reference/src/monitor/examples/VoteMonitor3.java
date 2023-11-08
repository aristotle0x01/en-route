package examples;

import monitor.*;

/**
 * <p>Title: Monitor package and examples</p>
 * <p>Description: Like VoteMonitor, but uses doWithin. </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Memorial University of Newfoundland</p>
 * @author Theodore S. Norvell
 * @version 1.0
 */

public class VoteMonitor3 extends AbstractMonitor implements VoteInterface {

    private final int N ;
    private int votesFor = 0, votesAgainst = 0 ;

    private Condition electionDone = makeCondition( new Assertion() {
        @Override public boolean isTrue() {
        	return votesFor+votesAgainst == N ; } } ) ;

    public VoteMonitor3(int N) {
    	assert N > 0 ;
        this.N = N ; }

    @Override protected boolean invariant() {
        return 0 <= votesFor && 0 <= votesAgainst
            && votesFor+votesAgainst < N  ; }

    public boolean castVoteAndWaitForResult(final boolean  vote) {
    	return doWithin( new RunnableWithResult<Boolean>() {
			public Boolean run() {
				if( vote ) votesFor++ ; else votesAgainst++ ;
				
		        electionDone.conditionalAwait() ;
		
		        // Assert: votesFor+votesAgainst == N
		        boolean result = votesFor > votesAgainst ;
		
		        if( ! electionDone.isEmpty() ) {
		            electionDone.signalAndLeave() ; }
		        else {
		            votesFor = votesAgainst = 0 ;}
		        // At this point the thread could be occupying
		        // the monitor, or not!
		        return result ;
			}} ) ; }
}