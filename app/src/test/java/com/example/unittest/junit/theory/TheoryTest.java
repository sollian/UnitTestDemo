package com.example.unittest.junit.theory;

import org.junit.Assume;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * 更灵活的参数化测试
 *
 * @author shouxianli on 2020/7/16.
 */
@RunWith(Theories.class)
public class TheoryTest {

    @DataPoints
    public static String[] names = {
            "John", "Peter"
    };
    @DataPoints
    public static int[] ages = {
            24, 29, -4
    };

    @Theory
    public void testParam(String name, int age) {
        //当条件不满足时，跳过当前测试，相当于continue
        Assume.assumeTrue(age > 0);
        System.out.println(name + " is " + age + " years old.");
    }
}
