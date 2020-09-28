package com.example.unittest.junit.theory;

import org.junit.Assume;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
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
    public static String[] name = {
            "John", "Peter"
    };

    @DataPoints("age")
    public static int[] getAges() {
        return new int[]{
                24, 29, -4
        };
    }

    @DataPoints("sex")
    public static int[] getSex() {
        return new int[]{
                0, 1
        };
    }


    @Theory
    public void testParam(String name, int age) {
        //当条件不满足时，跳过当前测试，相当于continue
        Assume.assumeTrue(age > 0);
        System.out.println(name + " is " + age + " years old.");
    }

    @Theory
    public void testParam2(String name, @FromDataPoints("age") int age, @FromDataPoints("sex") int sex) {
        //当条件不满足时，跳过当前测试，相当于continue
        Assume.assumeTrue(age > 0);
        System.out.println(name + " is " + age + " years old." + " sex:" + sex);
    }
}
