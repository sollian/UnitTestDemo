package com.example.unittest.junit;

import org.junit.Test;

/**
 * @author shouxianli on 2020/7/15.
 */
public class ApiJunitTest extends BaseApiTest {

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
     * @throws InterruptedException
     */
    @Test(timeout = 600)
    public void testTimeout() throws InterruptedException {
        api.timeout();
    }

    @Test
    public void testAssume() {
    }
}