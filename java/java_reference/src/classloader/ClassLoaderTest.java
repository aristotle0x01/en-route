package classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

public class ClassLoaderTest {
    public static void main(String[] args) {
        try {
            {
                System.out.println("##########:");
                // loadClass vs findClass不论是否自定义类加载器
                CustomClassLoader classLoader1 = new CustomClassLoader();
                Class<?> classl = classLoader1.loadClass("classloader.Test1");
                Class<?> classf = classLoader1.findClass("classloader.Test1");
                Class<?> classn = Test1.class;

                System.out.println("findClass:" + classf.getClassLoader());
                System.out.println("loadClass:" + classl.getClassLoader());
                System.out.println("classn: " + classn.getClassLoader());
                System.out.println("classl==classf:" + (classl==classf));
                System.out.println("classl equals classf:" + (classl.equals(classf)));
                System.out.println("classf==classn:" + (classf==classn));
                System.out.println("classf equals classn:" + (classf.equals(classn)));
                // classl 与 classn是完全相同的一个类对象
                System.out.println("classl==classn:" + (classl==classn));
                System.out.println("classl equals classn:" + (classl.equals(classn)));
                System.out.println("");
            }

            {
                System.out.println("##########:");
                // 不同加载器，则类不同
                CustomClassLoader classLoader1 = new CustomClassLoader();
                Class<?> class1 = classLoader1.findClass("classloader.Test1");

                CustomClassLoader classLoader2 = new CustomClassLoader();
                Class<?> class2 = classLoader2.findClass("classloader.Test1");

                System.out.println("classLoader1:" + class1.getClassLoader());
                System.out.println("classLoader2:" + class2.getClassLoader());
                System.out.println("class1:" + class1 + "@" + Integer.toHexString(class1.hashCode()));
                System.out.println("class2:" + class2 + "@" + Integer.toHexString(class2.hashCode()));
                System.out.println("class1==class2:" + (class1==class2));
                System.out.println("class1 equals class2:" + (class1.equals(class2)));

                System.out.println("");
            }

            {
                System.out.println("##########:");
                CustomClassLoader classLoader1 = new CustomClassLoader();
                Class<?> class1 = classLoader1.loadClass("classloader.Test1");
                Class<?> classf = classLoader1.findClass("classloader.Test1");
                System.out.println("findClass:" + classf.getClassLoader());
                System.out.println("loadClass:" + class1.getClassLoader());
                System.out.println("==:" + (class1.equals(classf)));

                Class<?> class2 = Test1.class;
                System.out.println("class1: " + class1.getClassLoader());
                System.out.println("class2: " + class2.getClassLoader());
                System.out.println("equals: " + (class1 == class2));

                Test1 t3 = new Test1();
                Test1 t4 = new Test1();
                System.out.println("t3==t4: " + (t3 == t4));
                System.out.println("t3 equals t4: " + (t3.equals(t4)));

                Object o1 = class1.newInstance();
                Field f1 = class1.getField("a1");
                f1.setInt(o1, 1);
                Test1 o2 = (Test1) class2.newInstance();
                Field f2 = class2.getField("a1");
                f2.setInt(o2, 1);
                System.out.println("o1 equals o2: " + (o2.equals(o1)));
                Object of = classf.newInstance();
                Field ff = classf.getField("a1");
                ff.setInt(of, 1);
                System.out.println("o2 equals of: " + (o2.equals(of)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class CustomClassLoader extends ClassLoader {

        @Override
        public Class findClass(String name) throws ClassNotFoundException {
            byte[] b = loadClassFromFile(name);
            return defineClass(name, b, 0, b.length);
        }

        private byte[] loadClassFromFile(String fileName)  {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
                    fileName.replace('.', File.separatorChar) + ".class");
            byte[] buffer;
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            int nextValue = 0;
            try {
                while ( (nextValue = inputStream.read()) != -1 ) {
                    byteStream.write(nextValue);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            buffer = byteStream.toByteArray();
            return buffer;
        }
    }
}