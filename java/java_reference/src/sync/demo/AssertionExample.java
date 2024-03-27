package sync.demo;
import sync.Assertion;

public class AssertionExample {
  int x = 0, y=0 ;
  void test() {
      class MyAssertion extends Assertion {

          public boolean isTrue() { return x==y ; } }

      Assertion a = new MyAssertion() ;
      System.out.println( a.isTrue() ) ; // Prints true.
      a.check() ; // no effect
      x = 1 ;
      System.out.println( a.isTrue() ) ; // Prints false.
      a.check() ; // Throws an object of class Error.
  }
  public static void main(String[] args) {
    AssertionExample ex = new AssertionExample() ;
    ex.test() ;
  }
}