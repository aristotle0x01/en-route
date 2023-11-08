package postman.classloader.hotswap;

public class A {

    private postman.classloader.hotswap.B b;

    public void setB(postman.classloader.hotswap.B b) {
        this.b = b;
    }

    public postman.classloader.hotswap.B getB() {
        return b;
    }
}