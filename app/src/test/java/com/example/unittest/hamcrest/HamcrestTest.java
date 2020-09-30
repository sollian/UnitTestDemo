package com.example.unittest.hamcrest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试类
 *
 * @author shouxianli on 2020/9/28.
 */
public class HamcrestTest {

    /**
     * 字符串相关
     */
    @Test
    public void testString() {
        Assert.assertThat("Hello", Matchers.startsWith("Hel"));
        Assert.assertThat("Hello", Matchers.endsWith("llo"));
        Assert.assertThat("Hello", Matchers.containsString("ell"));
        Assert.assertThat("", Matchers.isEmptyString());
        Assert.assertThat(null, Matchers.isEmptyOrNullString());
    }

    /**
     * 满足一组匹配器
     */
    @Test
    public void testAllOf() {
        Assert.assertThat("Hello World", Matchers.allOf(
                Matchers.startsWith("Hello"),
                Matchers.endsWith("rld"),
                Matchers.containsString("Wor"),
                Matchers.is("Hello World")
        ));
    }

    /**
     * 满足任意一个匹配器
     */
    @Test
    public void testAnyOf() {
        Assert.assertThat("Hello World", Matchers.anyOf(
                Matchers.startsWith("Hello_"),
                Matchers.endsWith("_rld"),
                Matchers.containsString("Wor"),
                Matchers.is("Hello_World")
        ));
    }

    /**
     * 满足任意两个匹配器
     */
    @Test
    public void testBoth() {
        Assert.assertThat("Hello World",
                Matchers.both(Matchers.startsWith("Hel")).and(Matchers.endsWith("rld")));
    }

    /**
     * 满足两个当中的任意一个匹配器
     */
    @Test
    public void testEither() {
        Assert.assertThat("Hello World",
                Matchers.either(Matchers.startsWith("Hel_")).or(Matchers.endsWith("rld")));
    }

    @Test
    public void testIs() {
        Assert.assertThat("Hello", Matchers.is(Matchers.startsWith("Hel")));

        /*
        is是is(equalTo())的简写，与equalTo完全相同，
        通过调用对象的equal方法判断是否相等
         */
        Assert.assertThat("Hello", Matchers.is("Hello"));
        Assert.assertThat("Hello", Matchers.equalTo("Hello"));
        //若要判断是同一个对象，可使用sameInstance或者theInstance（两者完全相同）
        String newStr = "Hello";
        Assert.assertThat(newStr, Matchers.is("Hello"));
        //下面断言失败
//        Assert.assertThat(newStr, Matchers.sameInstance("Hello"));

        /*
        下面三个完全一样
        is和isA都是is(instanceof())的简写
         */
        Assert.assertThat("Hello", Matchers.is(String.class));
        Assert.assertThat("Hello", Matchers.isA(String.class));
        Assert.assertThat("Hello", Matchers.instanceOf(String.class));
    }

    @Test
    public void testNot() {
        Assert.assertThat("Hello", Matchers.not(Matchers.startsWith("Hel_")));
        //not是not(equalTo())的简写
        Assert.assertThat("Hello", Matchers.not("World"));
    }

    //集合相关

    /**
     * 对集合的每一项做匹配
     */
    @Test
    public void testEveryItem() {
        Assert.assertThat(Arrays.asList("one", "two"),
                Matchers.everyItem(Matchers.containsString("o")));
    }

    /**
     * 对数组的每一项做匹配
     */
    @Test
    public void testArray() {
        Assert.assertThat(new Integer[]{1, 2, 3},
                Matchers.array(
                        Matchers.equalTo(1),
                        Matchers.equalTo(2),
                        Matchers.equalTo(3)
                )
        );
    }

    /**
     * 集合中是否包含
     */
    @Test
    public void testHasItem() {
        List<String> list = Arrays.asList("one", "two", "three");
        Assert.assertThat(list, Matchers.hasItem("two"));
        Assert.assertThat(list, Matchers.hasItem(Matchers.startsWith("on")));
        Assert.assertThat(list, Matchers.hasItems("two", "three"));
    }

    /**
     * 列表中是否包含
     */
    @Test
    public void testHasItemInArray() {
        Assert.assertThat(new Integer[]{1, 2, 3}, Matchers.hasItemInArray(2));
    }

    /**
     * 测试集合的全部元素
     */
    @Test
    public void testContains() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        Assert.assertThat(list, Matchers.contains(1, 2, 3));
        Assert.assertThat(list, Matchers.containsInAnyOrder(3, 2, 1));
    }

    /**
     * 测试数组的全部元素
     */
    @Test
    public void testArrayContaining() {
        Assert.assertThat(new Integer[]{1, 2, 3}, Matchers.arrayContaining(1, 2, 3));
        Assert.assertThat(new Integer[]{1, 2, 3}, Matchers.arrayContainingInAnyOrder(3, 2, 1));
    }

    /**
     * map相关
     */
    @Test
    public void testMap() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "Amy");
        Assert.assertThat(map, Matchers.hasEntry("name", "Amy"));
        Assert.assertThat(map, Matchers.hasKey("name"));
        Assert.assertThat(map, Matchers.hasValue("Amy"));
    }
}
