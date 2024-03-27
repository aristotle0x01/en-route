package sync.demo;

/**
 * <p>Title: Monitor package and examples</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Memorial University of Newfoundland</p>
 * @author Theodore S. Norvell
 * @version 1.0
 */
public class DriveVote {

    public static void main(String[] args) {
        //VoteInterface vm = new VoteMonitor(3) ;
        //VoteInterface vm = new VoteMonitor2(3) ;
        VoteInterface vm = new VoteMonitor3(3) ;
        Voter v0 = new Voter0(vm) ;
        Voter v1 = new Voter1(vm) ;
        Voter v2 = new Voter2(vm) ;

        v0.start() ;
        v1.start() ;
        v2.start() ;
    }
}

abstract class Voter extends TestThread {

    private VoteInterface vm ;

    Voter( VoteInterface vm ) { this.vm = vm ; }

    public void run() {
        for( int i = 0 ; i < 1000000000 ; ++i ) {
            boolean vote = makeVote(i) ;
            boolean consensus = vm.castVoteAndWaitForResult(vote) ;
            boolean expected = i%6==1 || i%6==2 || i%6==5 ;
            if( expected != consensus ) {
            	System.out.println("Failed");
            	System.exit(1) ; }
            if( i%100000==0) System.out.println(i+": "+consensus);
        }
        System.out.println("Done") ; }

    abstract boolean makeVote(int i) ;
}

class Voter0 extends Voter {
    Voter0( VoteInterface vm ) { super(vm) ; }
    boolean makeVote( int i ) { return i%2==1 ; }
}

class Voter1 extends Voter {
    Voter1( VoteInterface vm ) { super(vm) ; }
    boolean makeVote( int i ) { return i%3==2 ; }
}

class Voter2 extends Voter {
    Voter2( VoteInterface vm ) { super(vm) ; }
    boolean makeVote( int i ) { return i%3!=0 ; }
}