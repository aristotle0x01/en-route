package interview.yuaiweiwu.pool;

public class MyObjectFactory implements ObjectFactory<MyObject> {
    @Override
    public MyObject createObject() {
        // 在这里编写创建对象的逻辑
        return new MyObject();
    }
}
