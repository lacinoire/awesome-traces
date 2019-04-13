
// identify all tests methods
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.Launcher;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.ModifierKind;
import groovy.json.JsonSlurper


// first we collect all test methods

class Lister extends AbstractProcessor<CtMethod> {
  List result=[]
  boolean isToBeProcessed(CtMethod c) {
    return c.getAnnotation(org.junit.Test.class)!=null;
  }
  void process(CtMethod c) {
    result.add(c.getParent().getQualifiedName()+"#"+c.getSimpleName());
  }
}
def l = new Launcher();
l.addInputResource("src/test/java");
def proc = new Lister();
l.addProcessor(proc);
l.getEnvironment().setNoClasspath(true);
l.run();

// printing the collected tests

// second, we generate commands to be executed in shell
for(test in proc.result) {
    java_command = "java -cp /usr/share/java/junit4.jar:.:target/classes:target/test-classes SingleJUnitTestRunner '"+test+"'"

    btrace_command = "/tmp/btrace/bin/btracer -o 'btrace_traces/"+test+".methods.btrace' BtraceAllMethods.class -cp /usr/share/java/junit4.jar:.:target/classes:target/test-classes SingleJUnitTestRunner '"+test+"'"
    println(btrace_command)

    btraceall_command = "/tmp/btrace/bin/btracer -o 'btrace_traces/"+test+".spectrum.btrace' BtraceAllLines.class -cp /usr/share/java/junit4.jar:.:target/classes:target/test-classes SingleJUnitTestRunner '"+test+"'"
    println(btraceall_command)

//     proc = btrace_command.execute()
//     proc.waitFor()
//     println "Process exit code: ${proc.exitValue()}"
//     println "Std Err: ${proc.err.text}"
//     println "Std Out: ${proc.in.text}" 
}
