README 
====

```
javac -h . src/main/java/com/damonyuan/jni/HelloWorldJNI.java
cmake -S . -B build
cmake --build build
cmake --build build --target test
cmake --install build --prefix ~/cbin
qltest
```

```
javac -h . HelloWorldJNI.java
g++ -v -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/darwin com_damonyuan_jni_HelloWorldJNI.cpp -o com_damonyuan_jni_HelloWorldJNI.o
g++ -v -dynamiclib -o libsbmc.dylib com_damonyuan_jni_HelloWorldJNI.o  -lc
```
