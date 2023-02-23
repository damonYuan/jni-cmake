package com.damonyuan.jni;

public class HelloWorldJNI {
    static {
        try {
            System.loadLibrary("sbmc");
        } catch (final UnsatisfiedLinkError ex) {
            System.out.println("error: " + ex.getCause());
        }

    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.library.path"));
        new HelloWorldJNI().sayHello();
    }

    // Declare a native method sayHello() that receives no arguments and returns void
    private native void sayHello();
}
