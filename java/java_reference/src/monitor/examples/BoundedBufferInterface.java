package examples;

/**
 * <p>Title: Monitor package and examples</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Memorial University of Newfoundland</p>
 * @author Theodore S. Norvell
 * @version 1.0
 */

public interface BoundedBufferInterface {
    public void deposit(char data)  throws InterruptedException ;
    public char fetch()  throws InterruptedException ;
}