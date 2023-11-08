package classloader.test.bean;

public class ClassLoaderTest {

    /**
     * sun.misc.Launcher$ExtClassLoader@5ab8df17
     * "E:\jdk1.6\jdk1.6.0_38\bin\jar"  cvf testbean.jar .
     */

    public static void main(String[] args) {
        try {
            //查看当前系统类路径中包含的路径条目
            System.out.println(System.getProperty("java.class.path"));
            //调用加载当前类的类加载器（这里即为系统类加载器）加载TestBean
            Class typeLoaded = Class.forName("classloader.test.bean.TestBean");
            //查看被加载的TestBean类型是被那个类加载器加载的
            System.out.println(typeLoaded.getClassLoader());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
