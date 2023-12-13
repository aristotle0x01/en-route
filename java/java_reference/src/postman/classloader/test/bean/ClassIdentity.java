package postman.classloader.test.bean;

public class ClassIdentity {

    public static void main(String[] args) {
        new ClassIdentity().testClassIdentity();
    }

    public void testClassIdentity() {
        String classDataRootPath = "E:\\";
        classloader.test.bean.FileSystemClassLoader fscl1 = new classloader.test.bean.FileSystemClassLoader(classDataRootPath);
        classloader.test.bean.FileSystemClassLoader fscl2 = new classloader.test.bean.FileSystemClassLoader(classDataRootPath);
        String className = "xxx.Person";
        try {
            // findLoadedClass in loadClass:
            /*
             * Returns the class with the given <a href="#name">binary name</a> if this
             * loader has been recorded by the Java virtual machine as an initiating
             * loader of a class with that <a href="#name">binary name</a>.  Otherwise
             * <tt>null</tt> is returned.
             *
             * @param  name
             *         The <a href="#name">binary name</a> of the class
             *
             * @return  The <tt>Class</tt> object, or <tt>null</tt> if the class has
             *          not been loaded
             *
             * @since  1.1
            protected final Class<?> findLoadedClass(String name) {
                if (!checkName(name))
                    return null;
                return findLoadedClass0(name);
            }
             */
            Class<?> class1 = fscl1.loadClass(className);

            // class3 不会被重新加载
            Class<?> class3 = fscl1.loadClass(className);
            Object obj1 = class1.newInstance();

            // fscl2不同于fscl1，如上解释，class2要重新加载
            Class<?> class2 = fscl2.loadClass(className);
            Object obj2 = class2.newInstance();

            System.out.println(class1.equals(class2));
            class1.getMethod("setPerson", Object.class).invoke(obj1, obj2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}