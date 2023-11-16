package classloader;

public class Test1 {
    public int a1=1;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass())
            return false;
        return a1 == (((Test1)(obj)).a1);
    }

    @Override
    public int hashCode() {
        return a1;
    }
}