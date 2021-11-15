I also cloned jsoup into the root as an example project :)


# Collection of traces

## Repo explanation

* `SingleJUnitTestRunner.java` contains a main method to run a single test class
* `BtraceAllMethods.java`: Btrace script to be compiled with `btracec`
* `run-all-tests-in-single-jvm.groovy` generates one command per test

## Btrace

Prequisites:

* make sure you have $JAVA_HOME: `export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/`
* download and unzip Btrace (eg in `btrace`)
* download [jacoco](https://github.com/jacoco/jacoco/releases)
* Compile the Btrace tracing scripts: 
  * `btrace/bin/btracec BtraceAllMethods.java`
  * `javac -cp junit-jupiter-api-5.7.1.jar:junit-jupiter-engine-5.7.1.jar:junit-platform-launcher-1.7.1.jar:junit-platform-engine-1.7.1.jar:junit-platform-commons-1.7.1.jar SingleJUnitTestRunner.java`


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

## Tracing with BTrace

Test one simple tracing with btrace:

    mkdir btrace_traces/
    btrace/bin/btracer -v --stdout -o btrace_traces/simpletrace.btrace BtraceAllMethods.class -cp .:target/classes:target/test-classes:junit-jupiter-api-5.7.1.jar:junit-jupiter-engine-5.7.1.jar:junit-platform-commons-1.7.1.jar:junit-platform-launcher-1.7.1.jar:junit-platform-engine-1.7.1.jar SingleJUnitTestRunner 'org.jsoup.nodes.AttributeTest#html'

    java -javaagent:btrace/libs/btrace-agent.jar=script=AllMethods.class -cp .:target/classes:target/test-classes:junit-jupiter-api-5.7.1.jar:junit-jupiter-engine-5.7.1.jar:junit-platform-commons-1.7.1.jar:junit-platform-launcher-1.7.1.jar:junit-platform-engine-1.7.1.jar SingleJUnitTestRunner 'org.jsoup.nodes.AttributeTest#html'

    btrace/bin/btracer -v --stdout -o btrace_traces/simpletrace.btrace BtraceAllMethods.class -cp junit-jupiter-api-5.7.1.jar:junit-jupiter-engine-5.7.1.jar:junit-platform-launcher-1.7.1.jar:junit-platform-engine-1.7.1.jar:junit-platform-commons-1.7.1.jar:hamcrest-2.2.jar:.:target/classes:target/test-classes SingleJUnitTestRunner 'org.jsoup.nodes.AttributeTest#html'
    cat btrace_traces/simpletrace.btrace 

    btrace/bin/btracer -v --stdout -o btrace_traces/simpletrace.btrace AllMethods.class -cp .:target/classes:target/test-classes:junit-jupiter-api-5.7.1.jar:junit-jupiter-engine-5.7.1.jar:junit-platform-commons-1.7.1.jar:junit-platform-launcher-1.7.1.jar:junit-platform-engine-1.7.1.jar SingleJUnitTestRunner 'introclassJava.Syllables_WhiteboxTest#test9'

We want one trace per test, which means one process per test, for this we generate a file `tracing-commands.sh`

    groovy -cp /usr/share/java/junit4.jar:/home/martin/spoon-core-6.1.0-jar-with-dependencies.jar run-all-tests-in-single-jvm.groovy | tee tracing-commands.sh

And finally we trace all tests:

    sh tracing-commands.sh
    # check that we have something
    cat btrace_traces/*

## Tracing with Jacoco

Test one simple tracing with btrace:

```sh
mkdir jacoco_traces/
java -javaagent:jacoco/lib/jacocoagent.jar=destfile=jacoco_traces/jacoco-example.exec -cp .:target/classes:target/test-classes:junit-jupiter-api-5.7.1.jar:junit-jupiter-engine-5.7.1.jar:junit-platform-commons-1.7.1.jar:junit-platform-launcher-1.7.1.jar:junit-platform-engine-1.7.1.jar SingleJUnitTestRunner 'introclassJava.Syllables_WhiteboxTest#test9'
cat jacoco_traces/jacoco-example.exec

# looking at the traces
java -jar jacoco/lib/jacococli.jar report jacoco_traces/jacoco-example.exec --classfiles target/classes --csv jacoco_traces/jacoco-example.csv

```    

Tracing all tests with JUnit and Jacoco: TODO


## Tracing with GZoltar

TODO

