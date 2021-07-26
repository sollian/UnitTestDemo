package com.example.unittest.kotlin;

/**
 * @author shouxianli on 2021/5/11.
 */
public class UtilJava {

    static {
        System.out.println("UtilJava#静态初始化块");
    }

    {
        System.out.println("UtilJava#初始化块");
    }

    public UtilJava() {
        System.out.println("UtilJava#new");
    }

    public static UtilJava create() {
        System.out.println("UtilJava#create");
        UtilJavaDenpendency.func();
        return new UtilJava();
    }

    public static String ok() {
        return "UtilJava.ok()";
    }
}
