# sandbox-waf-for-java

## Description

A sample [waf](https://github.com/waf-project/waf) build script for Java application (Maven style).

## Requirements

- Python 2.7
- Java 8

## Usage

### configure

`configure` task configures the waf project.

```bash
$ ./waf configure
Setting top to                           : $HOME/sandbox-waf-for-java
Setting out to                           : $HOME/sandbox-waf-for-java/build
Checking for program 'javac'             : $JAVA_HOME/bin/javac
Checking for program 'java'              : $JAVA_HOME/bin/java
Checking for program 'jar'               : $JAVA_HOME/bin/jar
Checking for program 'javadoc'           : $JAVA_HOME/bin/javadoc
'configure' finished successfully (0.050s)
```

### build

`build` task compiles Java sources, run JUnit test cases, create Jar file and genrate Javadoc.

```bash
$ ./waf build
[1/7] Compiling src/main/resources/locale/messages.properties
[2/7] Compiling src/main/resources/logback.xml
[4/7] Running javadoc: src/main/java -> javadoc

[4/7] Processing javac: src/main/java/com/example/Main.java src/main/java/com/example/foo/Hello.java src/main/java/org/example/Hoge.java
[5/7] Processing src/test/java/com/example/foo/HelloTest.java
[6/7] Processing src/test/java/com/example/foo/HelloTest.java
JUnit version 4.12
.
Time: 0.729

OK (1 test)


[7/7] Processing jar_create: build/classes/main/com/example/Main.class build/classes/main/com/example/foo/Hello.class build/classes/main/locale/messages.properties build/classes/main/logback.xml build/classes/main/org/example/Hoge.class -> build/example-1.0.0.jar
Waf: Leaving directory `$HOME/sandbox-waf-for-java/build'
'build' finished successfully (5.820s)
```

### run_main

`run_main` task runs the application using Jar file under the build directory.

```bash
$ ./waf run_main
...
'run_main' finished successfully (0.494s)

```

## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## License

MIT
