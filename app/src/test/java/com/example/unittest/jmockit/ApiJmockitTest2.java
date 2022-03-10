package com.example.unittest.jmockit;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import mockit.Injectable;
import mockit.Mocked;
import org.junit.Assert;
import org.junit.Test;

/**
 * Mocked Injectable演示
 * @author shouxianli on 2020/11/24.
 */
public class ApiJmockitTest2 {
    @Test
    public void testClass(@Mocked Locale locale) {
        // 静态方法返回null
        Assert.assertNull(Locale.getDefault());
        // 非静态方法（返回类型为String）返回null
        Assert.assertNull(locale.getCountry());
        // 自已new一个，也同样如此，方法都被mock了
        Locale chinaLocale = new Locale("zh", "CN");
        Assert.assertNull(chinaLocale.getCountry());
        // 返回类型为对象的，也是null
        Assert.assertNull(chinaLocale.clone());
    }

    @Test
    public void testClass2(@Injectable Locale locale) {
        // 静态方法不mock
        Assert.assertNotNull(Locale.getDefault());
        // 非静态方法（返回类型为String）也不起作用了，返回了null,但仅仅限于locale这个对象
        Assert.assertNull(locale.getCountry());
        // 自已new一个，并不受影响
        Locale chinaLocale = new Locale("zh", "CN");
        Assert.assertEquals("CN", chinaLocale.getCountry());
    }

    @Test
    public void testInterface(@Mocked Map<String, String> map) {
        //返回值类型为String时，返回null
        Assert.assertNull(map.get("1"));
        Assert.assertEquals(0, map.size());
        //返回值类型为对象时，会返回mock的对象
        Assert.assertNotNull(map.entrySet());
        Assert.assertEquals(0, map.entrySet().size());
    }
}
