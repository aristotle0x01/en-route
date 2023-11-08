package classloader.test.bean;

import java.io.InputStream;

public class TestBean {

    public TestBean() { }

    public static void main(String[] args) throws Exception {
        // 一个简单的类加载器，逆向双亲委派机制
        // 可以加载与自己在同一路径下的Class文件
        ClassLoader myClassLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name)
                    throws ClassNotFoundException {
                try {
                    String filename = name.substring(name.lastIndexOf(".") + 1)
                            + ".class";
                    InputStream is = getClass().getResourceAsStream(filename);
                    if (is == null) {
                        return super.loadClass(name);   // 递归调用父类加载器
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (Exception e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj = myClassLoader.loadClass("classloader.test.bean.TestBean")
                .newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof classloader.test.bean.TestBean);

        // Exception in thread "main" java.lang.ClassCastException: classloader.test.bean.TestBean cannot be cast to classloader.test.bean.TestBean
        classloader.test.bean.TestBean b1 = new classloader.test.bean.TestBean();
        b1 = (classloader.test.bean.TestBean)obj;
    }
}
