package proxy;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.objectweb.asm.ClassWriter;

import java.io.FileOutputStream;
import java.lang.reflect.Method;

public class CglibProxyTest {
    public static void main(String[] args) throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserServiceImpl.class);
        enhancer.setCallback(new MyInterceptor());

        UserServiceImpl proxy = (UserServiceImpl) enhancer.create();

        // ask: https://www.perplexity.ai/
        //  Q: how to save a cglib enhanced class to .class file
        DebuggingClassWriter classWriter = new DebuggingClassWriter(ClassWriter.COMPUTE_MAXS);
        enhancer.generateClass(classWriter);
        byte[] bytecode = classWriter.toByteArray();
        try (FileOutputStream fos = new FileOutputStream("EnhancedClass.class")) {
            fos.write(bytecode);
        }
        proxy.select();
        proxy.update();
    }

    static class MyInterceptor implements MethodInterceptor{
        @Override
        public Object intercept(Object obj, Method method, Object[] params, MethodProxy proxy) throws Throwable {
            System.out.println("before:");
            Object result = proxy.invokeSuper(obj, params);
            System.out.println(" after:"+result);
            return result;
        }
    }
}
