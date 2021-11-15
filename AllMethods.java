
import org.openjdk.btrace.core.annotations.*;
import static org.openjdk.btrace.core.BTraceUtils.*;

/**
 * This script traces method entry
 */
@BTrace public class AllMethods {
    // @OnMethod(
    //     clazz="/introclassJava.*/",
    //     method="/.*/"
    // )
    @OnExit
    public static void m(int code) {//(@ProbeClassName String probeClass, @ProbeMethodName String probeMethod) {
        // print(Strings.strcat("entered ", probeClass));
        // println(Strings.strcat(".", probeMethod));
        println("BTrace program exits!");
    }
}

