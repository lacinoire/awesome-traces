// inspired from https://github.com/Ultimanecat/DefectRepairing/blob/master/tool/source/lib/btrace/AllLines_pattern.java
import com.sun.btrace.annotations.*;
import static com.sun.btrace.BTraceUtils.*;

@BTrace
public class BtraceAllLines {
    @OnMethod(
        clazz="/introclassJava.*/",
        method="/.*/",
        location=@Location(value=Kind.LINE, line=-1)
    )
    public static void online(@ProbeClassName String pcn, @ProbeMethodName String pmn, int line) {
        print("---" + pcn + "." + pmn +  ":" + line + "\n");
    }
    
}
