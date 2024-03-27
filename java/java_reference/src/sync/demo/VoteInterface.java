package sync.demo;

/**
 * <p>Title: Monitor package and examples</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Memorial University of Newfoundland</p>
 * @author Theodore S. Norvell
 * @version 1.0
 */

public interface VoteInterface {
    public boolean castVoteAndWaitForResult(boolean vote) ;
}