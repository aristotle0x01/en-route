package bytecode;

//-Xint：强制使用纯解释执行模式，而不进行即时编译（JIT）优化。这会导致应用程序在运行时速度较慢，因为它不会使用JIT编译器来提高执行效率。
//-Xmixed：默认选项，启用混合模式执行。这允许JVM在运行时根据代码的热度和执行情况选择解释执行或即时编译来获得更好的性能。
//-Xint:methodName：针对特定方法启用纯解释执行模式。可以指定要在解释执行模式下执行的特定方法的名称。

// -Xint -XX:-UseCompressedOops -XX:+UnlockDiagnosticVMOptions -XX:+PrintInterpreter
public class AddressTest {
    public static void main(String args[]) throws Exception {
        int i = 1;
        System.out.println(i);
    }
}



















