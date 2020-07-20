package com.example.unittest.junit;

import com.example.unittest.Api;
import org.junit.BeforeClass;

/**
 * @author shouxianli on 2020/7/15.
 */
public abstract class BaseApiTest {

    protected static Api api;

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
