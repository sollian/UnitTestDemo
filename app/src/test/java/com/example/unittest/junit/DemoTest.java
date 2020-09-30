package com.example.unittest.junit;

import com.example.unittest.data.Api;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 测试类
 *
 * @author shouxianli on 2020/9/17.
 */
public class DemoTest {

    private static Api api;

    @BeforeClass
    public static void onCreate() {
        System.out.println("onCreate");
        api = new Api();
    }

    @Before
    public void onResume() {
        System.out.println("onResume");
    }

    @After
    public void onPause() {
        System.out.println("onPause");
    }

    @AfterClass
    public static void onDestroy() {
        System.out.println("onDestroy");
    }

    @Test
    public void testAdd() {
        System.out.println("testAdd--");
        int result = api.add(2, 4);
        Assert.assertEquals(6, result);
    }

    @Test
    public void testMinus() {
        System.out.println("testMinus--");
        int result = api.minus(6, 4);
        Assert.assertEquals(2, result);
    }
}
