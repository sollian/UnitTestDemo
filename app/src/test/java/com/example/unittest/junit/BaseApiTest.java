package com.example.unittest.junit;

import com.example.unittest.data.Api;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * 测试类
 *
 * @author shouxianli on 2020/7/15.
 */
public abstract class BaseApiTest {
    private static final String TAG = "BaseApiTest";

    protected static Api api;

    @Before
    public void setup11() {
        System.out.println(TAG + " before");
    }

    @After
    public void clear11() {
        System.out.println(TAG + " after");
    }

    /**
     * 每个测试方法都会重新创建类
     */
    public BaseApiTest() {
//        System.out.println("调用构造方法");
    }

    @BeforeClass
    public static void create() {
        api = new Api();
//        api.create();
    }
//
//    @Before
//    public void resume() {
//        api.connect();
//    }
//
//    @After
//    public void pause() {
//        api.closeConnection();
//    }
//
//    @AfterClass
//    public static void destroy() {
//        api.destroy();
//    }
}
