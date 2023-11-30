package classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ClassLoaderTest2 {
    public static void main(String[] args) {
        try {
            {
                Class<?> class2 = Test2.class;
                CustomClassLoader classLoader1 = new CustomClassLoader();
                Class<?> class1 = classLoader1.findClass("classloader.ClassLoaderTest2$Test2");
                System.out.println("class1: " + class1.getClassLoader());
                System.out.println("class2: " + class2.getClassLoader());
                System.out.println("equals: " + (class1 == class2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Test2 {
        public int a1=1;

        @Override
        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (getClass() != obj.getClass())
                return false;
            return a1 == (((Test2)(obj)).a1);
        }

        @Override
        public int hashCode() {
            return a1;
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