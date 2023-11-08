package examples;

class Printer extends Thread {
    private String message ;

    public Printer( String m ) { message = m ; }

    public void run() { for( int i=0 ; i<1000 ; ++i ) System.out.println(message) ; }
}

public class HiHo {
    public static void main(String[] args) {

        Thread t0 = new Printer("Hi") ;
        Thread t1 = new Printer("Ho") ;
        t0.start() ;
        t1.start() ; }
}