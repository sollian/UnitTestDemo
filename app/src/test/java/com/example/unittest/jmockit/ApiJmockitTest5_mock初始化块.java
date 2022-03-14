package com.example.unittest.jmockit;

import mockit.Mock;
import mockit.MockUp;
import org.junit.Assert;
import org.junit.Test;

/**
 * 初始化块、构造函数mock演示
 *
 * @author shouxianli on 2020/11/24.
 */
public class ApiJmockitTest5_mock初始化块 {

    // AnOrdinaryClassWithBlock的MockUp类，继承MockUp即可
    public static class AnOrdinaryClassWithBlockMockUp extends MockUp<AnOrdinaryClassWithBlock> {

        /**
         * Mock构造函数和初始代码块, 函数名$init就代表类的构造函数
         *
         * int i是构造函数的参数
         */
        @Mock
        public void $init(int i) {
        }

        // Mock静态初始代码块, 函数名$clinit就代表类的静态代码块
        @Mock
        public void $clinit() {
        }
    }

    @Test
    public void testClassMockingByMockUp() {
        new AnOrdinaryClassWithBlockMockUp();
        AnOrdinaryClassWithBlock instance = new AnOrdinaryClassWithBlock(10);
        // 静态初始代码块被mock了
        Assert.assertEquals(0, AnOrdinaryClassWithBlock.j);
        // 构造函数和初始代码块被mock
        Assert.assertEquals(0, instance.getI());
    }
}

class AnOrdinaryClassWithBlock {

    private int i;

    public static int j;

    // 初始代码块
    {
        System.out.println("初始化块");
        i = 1;
    }

    // 静态初始代码块
    static {
        j = 2;
        System.out.println("静态初始化块");
    }

    // 构造函数
    public AnOrdinaryClassWithBlock(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

}