
// compile with "javac -cp /usr/share/java/junit4.jar SingleJUnitTestRunner.java"
import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.*;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherConfig;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import introclassJava.*;

import java.io.*;
import java.util.Collections;

public class SingleJUnitTestRunner {
    public static void main(String... args) throws ClassNotFoundException {
        String[] classAndMethod = args[0].split("#");
        int index = classAndMethod[0].lastIndexOf(".");
        String packageName = classAndMethod[0].substring(0, index);
        String className = classAndMethod[0].substring(index + 1);
        System.out.println(classAndMethod[0]+" "+classAndMethod[1]);

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
            .selectors(
                selectPackage("org.jsoup.nodes"),
                selectClass(SingleJUnitTestRunner.class.getClassLoader().loadClass("org.jsoup.nodes.AttributeTest"))
                // selectClasspathRoots(Collections.singleton(new File("/target/test-classes")))
                // selectMethod("org.jsoup.nodes.AttributeTest#html")
            )
            .filters(
                includeClassNamePatterns(".*Test")
            )
            .build();
            

        Launcher launcher = LauncherFactory.create();

        // Register a listener of your choice
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);

        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();
        // Do something with the TestExecutionSummary.
        System.out.println(summary.getTestsFoundCount());
        System.out.println(summary.getTestsStartedCount());
        System.out.println(summary.getFailures());
        System.out.println(summary.getFailures().get(0).getException());
        System.out.println(summary.getFailures().get(0).getTestIdentifier());

        // testitest();

        summary.printTo(new PrintWriter(System.out));

        // Request request = Request.method(Class.forName(classAndMethod[0]),classAndMethod[1]);

        // Result result = new JUnitCore().run(request);
        // for (Failure f : result.getFailures()) {
        //     System.out.println(f.getMessage());
        //     System.out.println(f.getTrace());
        // }
        System.exit(1);
    }

    public static void testitest() {
        System.out.println("teeeeeestiteeeeest");
    }
}
