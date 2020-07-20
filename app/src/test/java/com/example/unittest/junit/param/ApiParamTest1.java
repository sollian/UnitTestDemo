package com.example.unittest.junit.param;

import com.example.unittest.junit.BaseApiTest;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * 参数化测试1
 *
 * 通过注解传递参数
 *
 * @author shouxianli on 2020/7/15.
 */
@RunWith(Parameterized.class)
public class ApiParamTest1 extends BaseApiTest {

    /**
     * 第0个参数
     *
     * 必须是public的!!
     */
    @Parameter(0)
    public int num1;
    /**
     * 第一个参数
     */
    @Parameter(1)
    public int num2;
    /**
     * 第二个参数
     */
    @Parameter(2)
    public int result;

    /**
     * 要传递的参数，可以传递多组
     *
     * @return
     */
    @Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {3, 6, 9},
                {2, 4, 6},
                {4, 10, 15}
        });
    }

    @Test
    public void testAdd() {
        Assert.assertEquals(result, api.add(num1, num2));
        System.out.println("test result: " + result);
    }
}
