package com.example.unittest.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试类
 *
 * @author shouxianli on 2020/7/15.
 */
public class ApiJunitTest extends BaseApiTest {

    private static final String TAG = "ApiJunitTest";

    @Before
    public void setup() {
        System.out.println(TAG + " before");
    }

    @After
    public void clear() {
        System.out.println(TAG + " after");
    }

    /**
     * 抛出指定异常，则测试通过
     */
    @Test(expected = NullPointerException.class)
    public void testException() {
        api.exception();
    }

    /**
     * 规定时间内执行完毕，则测试通过
     *
     * @throws InterruptedException 抛出异常
     */
    @Test(timeout = 600)
    public void testTimeout() throws InterruptedException {
        api.timeout();
    }
}