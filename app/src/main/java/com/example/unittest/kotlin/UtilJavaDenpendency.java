package com.example.unittest.kotlin;

/**
 * @author shouxianli on 2021/7/21.
 */
public class UtilJavaDenpendency {

    static {
        System.out.println("UtilJavaDenpendency#静态初始化块");
    }

    public static void func() {
        System.out.println("UtilJavaDenpendency#func");
    }
}
