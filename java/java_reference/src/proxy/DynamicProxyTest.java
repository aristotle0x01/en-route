package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;

public class DynamicProxyTest {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        // to save the jdk generated proxy class use either way
        // way 1:
        // System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        UserService us = new UserServiceImpl();
        LogProxy proxy = new LogProxy();
        UserService enhanced = (UserService)proxy.bind(us);
        enhanced.select();
        enhanced.update();

        // way 2: save jdk generated proxy class
        // ProxyUtils.generateClassFile(us.getClass(), "DynamicProxyTest.DynProxy");
    }

    public static class LogProxy implements InvocationHandler {
        Object target;

        Object bind(Object obj) {
            this.target = obj;
            return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            intercept();
            Object result = method.invoke(target, args);
            return result;
        }

        private void intercept() {
            Date d = new Date();
            System.out.println("# start: " + d);
        }
    }
}
