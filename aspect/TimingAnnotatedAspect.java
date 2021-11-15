package aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@Aspect
public class TimingAnnotatedAspect {

    private static final Logger LOGGER
            = Logger.getLogger(TimingAnnotatedAspect.class.getName());

    // instance/class field ...

    public TimingAnnotatedAspect() {
        System.out.println(getClass() + " instantiated!");
    }

    @Pointcut("execution(* SingleJUnitTestRunner..*(..))")
    private void executionInService() {
        //do nothing, just for pointcut def
    }

    @After("executionInService()")
    public void before(JoinPoint jp) {

        LOGGER.info(String.format(
                "Entering Method {%s} of {%s}",
                jp.getSignature().getName(),
                jp.getSignature().getDeclaringType()));

        System.out.println("exec()!");
        System.out.println(jp);
    }

}
