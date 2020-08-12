package com.example.unittest;

import com.example.unittest.junit.ApiJunitTest;
import com.example.unittest.junit.param.ApiParamTest1;
import com.example.unittest.junit.param.ApiParamTest2;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * 测试套件，按顺序执行一组测试。
 * 测试套件可以嵌套
 *
 * @author shouxianli on 2020/7/15.
 */
@RunWith(Suite.class)
@SuiteClasses({
        ApiJunitTest.class,
        ApiParamTest1.class,
        ApiParamTest2.class,
})
public class ApiSuiteTest {

}
