package classloader.test.bean;

public class Sample {

    private Sample instance;

    public void setSample(Object instance) {
        System.out.println(instance.toString());
        this.instance = (Sample) instance;
    }
}