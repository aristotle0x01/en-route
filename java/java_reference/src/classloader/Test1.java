package classloader;

import java.util.Hashtable;

public class Test1 extends Base {
    public static int d = 2;
    public static final int d1 = 3;

    public int a1=1;

    public final String s = "ddd";

    private long[][] windingRoad = new long[5][5];
    private java.lang.Object[] stuff;
    private java.util.Hashtable ht = new Hashtable();
    private boolean[][][] isReady;

    private final Object a2 = new Object();

    public Test1() {}

    public Test1(int d_) {
        a1 = d_;
    }

    public Test1(int d_, int t_) {
        a1 = d_;
    }

    @Override
    public boolean equals(Object obj) {
        a2.hashCode();
        ht.hashCode();

        td();
        if (obj == null) return false;
        if (getClass() != obj.getClass())
            return false;
        return a1 == (((Test1)(obj)).a1);
    }

    private void td(){
        System.out.println("-");
    }

    @Override
    public int hashCode() {
        return a1;
    }


    static int read(byte[] b, int off, int len) {
        return 0;
    }

    boolean regionMatches (boolean ignoreCase, int
            toOffset, String other, int ooffset, int len){
        return false;
    }
}