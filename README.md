README 
====

Building JNI project using `cmake` on Mac M1.

## Build with cmake

```
javac -h . src/main/java/com/damonyuan/jni/HelloWorldJNI.java
cmake -S . -B build
cmake --build build
java -Djava.library.path=build -jar build/sbmcprj.jar
```

## Build with g++/clang

Follow this [Tutorial](https://www.baeldung.com/jni).

```
javac -h . HelloWorldJNI.java
g++ -v -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/darwin com_damonyuan_jni_HelloWorldJNI.cpp -o com_damonyuan_jni_HelloWorldJNI.o
g++ -v -dynamiclib -o libsbmc.dylib com_damonyuan_jni_HelloWorldJNI.o  -lc
```

## Notes

1. in Mac M1, please install the arm version of JDK. Confirm it with the following command
   ```
   # file /Library/Java/JavaVirtualMachines/openjdk-11.jdk/Contents/Home/bin/java
   /Library/Java/JavaVirtualMachines/openjdk-11.jdk/Contents/Home/bin/java: Mach-O 64-bit executable arm64
   ```