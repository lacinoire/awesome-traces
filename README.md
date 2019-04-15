# Collection of traces

## Repo explanation

* `SingleJUnitTestRunner.java` contains a main method to run a single test class
* `BtraceAllMethods.java`: Btrace script to be compiled with `btracec`
* `run-all-tests-in-single-jvm.groovy` generates one command per test

## Btrace

Prequisites:

* make sure you have $JAVA_HOME: `export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/`
* download and unzip Btrace (eg in `/tmp/btrace`)
* Compile the Btrace tracing scripts: 
  * `/tmp/btrace/bin/btracec BtraceAllMethods.java`
  * `javac -cp /usr/share/java/junit4.jar SingleJUnitTestRunner.java`


Download and compile programs to be traced:

    git clone https://github.com/Spirals-Team/IntroClassJava/
    # compile one example
    cd IntroClassJava/dataset/syllables/ref/reference
    # compile app code (src/main/java) and test code (src/test/java)
    mvn clean test compile -DskipTests
    cd ../../../../../
    # copy the sources in the current working dir
    cp -r IntroClassJava/dataset/syllables/ref/reference/src .
    # copy the compiled content in the current working dir
    cp -r IntroClassJava/dataset/syllables/ref/reference/target .

Test one simple tracing:

    mkdir btrace_traces/
    /tmp/btrace/bin/btracer -o btrace_traces/simpletrace.btrace BtraceAllMethods.class -cp /usr/share/java/junit4.jar:.:target/classes:target/test-classes SingleJUnitTestRunner 'introclassJava.Syllables_WhiteboxTest#test9'
    cat btrace_traces/simpletrace.btrace 

Now we can prepare on btrace process per test

    groovy -cp /usr/share/java/junit4.jar:/home/martin/spoon-core-6.1.0-jar-with-dependencies.jar run-all-tests-in-single-jvm.groovy | tee tracing-commands.sh

And finally we trace all tests:

    sh tracing-commands.sh
    # check that we have something
    cat btrace_traces/*

