// compile with "javac -cp /usr/share/java/junit4.jar SingleJUnitTestRunner.java"

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class SingleJUnitTestRunner {
    public static void main(String... args) throws ClassNotFoundException {
        String[] classAndMethod = args[0].split("#");
        //System.out.println(classAndMethod[0]+" "+classAndMethod[1]);
        Request request = Request.method(Class.forName(classAndMethod[0]),classAndMethod[1]);

        Result result = new JUnitCore().run(request);
        for (Failure f : result.getFailures()) {
            System.out.println(f.getMessage());
            System.out.println(f.getTrace());
        }
        System.exit(result.wasSuccessful() ? 0 : 1);
    }
}
