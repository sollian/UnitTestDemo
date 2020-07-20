package com.example.unittest.junit.param;

import com.example.unittest.junit.BaseApiTest;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * 参数化测试
 *
 * 通过构造函数传递参数
 *
 * @author shouxianli on 2020/7/15.
 */
@RunWith(Parameterized.class)
public class ApiParamTest2 extends BaseApiTest {

    private final int num1;
    private final int num2;
    private final int result;

    /**
     * 参数按顺序传递
     *
     * @param num1
     * @param num2
     * @param result
     */
    public ApiParamTest2(int num1, int num2, int result) {
        this.num1 = num1;
        this.num2 = num2;
        this.result = result;
        //下面的打印很有意思，运行ClassASuiteTest看看结果^-^
        System.out.println("实例：" + this + "，result:" + result);
    }

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
