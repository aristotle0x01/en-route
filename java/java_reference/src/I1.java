public interface I1 {
    int i2=1;

    void get();

    default void set(int ii){
        System.out.println(ii);
    }

}
