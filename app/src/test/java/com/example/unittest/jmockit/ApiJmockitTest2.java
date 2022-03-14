package com.example.unittest.jmockit;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.Assert;
import org.junit.Test;

/**
 * Mocked Injectable Mockup&@Mock Verifications演示
 *
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

    /**
     * 不支持mock单一实例
     */
    @Test
    public void testMockUp() {
        // 对Java自带类Calendar的get方法进行定制
        // 只需要把Calendar类传入MockUp类的构造函数即可
        new MockUp<Calendar>(Calendar.class) {
            // 想Mock哪个方法，就给哪个方法加上@Mock， 没有@Mock的方法，不受影响
            @Mock
            public int get(int unit) {
                if (unit == Calendar.YEAR) {
                    return 2017;
                }
                if (unit == Calendar.MONDAY) {
                    return 12;
                }
                if (unit == Calendar.DAY_OF_MONTH) {
                    return 25;
                }
                if (unit == Calendar.HOUR_OF_DAY) {
                    return 7;
                }
                return 0;
            }
        };
        // 从此Calendar的get方法，就沿用你定制过的逻辑，而不是它原先的逻辑。
        Calendar cal = Calendar.getInstance(Locale.FRANCE);
        Assert.assertEquals(2017, cal.get(Calendar.YEAR));
        Assert.assertEquals(12, cal.get(Calendar.MONDAY));
        Assert.assertEquals(25, cal.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(7, cal.get(Calendar.HOUR_OF_DAY));
        // Calendar的其它方法，不受影响
        Assert.assertEquals(Calendar.MONDAY, cal.getFirstDayOfWeek());
    }

    /**
     * 只对满足条件的使用新逻辑，其他的使用原逻辑
     */
    @Test
    public void testMockUp2() {
        // 对Java自带类Calendar的get方法进行定制
        new MockUp<Calendar>(Calendar.class) {
            // 申明参数invocation，表示老方法的调用
            @Mock
            public int get(Invocation invocation, int unit) {
                // 只希望时间是早上7点
                if (unit == Calendar.HOUR_OF_DAY) {
                    return 7;
                }
                // 其它时间（年份，月份，日，分，秒均不变)
                return invocation.proceed(unit);
            }
        };
        Calendar now = Calendar.getInstance();
        // 只有小时变成Mock方法
        Assert.assertEquals(7, now.get(Calendar.HOUR_OF_DAY));
        // 其它的还是走老的方法
        Assert.assertEquals(now.get(Calendar.MONTH), new Date().getMonth());
        Assert.assertEquals(now.get(Calendar.DAY_OF_MONTH), new Date().getDate());
    }

    ////////////AOP处理

    // 测试SayHello类每个方法的时间性能
    @Test
    public void testSayHelloCostPerformance() {
        // 把方法的调用时间记录到costMap中。key是方法名称，value是平均调用时间
        Map<String, Long> costMap = new HashMap<>();
        new MockUp<SayHello>() {
            @Mock
            public Object $advice(Invocation invocation) {
                long a = System.currentTimeMillis();
                Object result = invocation.proceed();
                long cost = System.currentTimeMillis() - a;
                // 把某方法的平均调用时间记录下来
                String methodName = invocation.getInvokedMember().getName();
                Long preCost = costMap.get(methodName);
                if (preCost == null) {
                    costMap.put(methodName, cost);
                } else {
                    costMap.put(methodName, (preCost + cost) / 2);
                }
                return result;
            }
        };
        SayHello sayHello = new SayHello();
        sayHello.sayHello("david", ISayHello.MALE);
        sayHello.sayHello("lucy", ISayHello.FEMALE);
        for (Long time : costMap.values()) {
            // 期望每个方法的调用时间不超过20ms
            Assert.assertTrue(time < 20);
        }
    }

    //////////Verifications验证

    @Test
    public void testVerification() {
        // 录制阶段
        Calendar cal = Calendar.getInstance();
        new Expectations(Calendar.class) {
            {
                // 对cal.get方法进行录制，并匹配参数 Calendar.YEAR
                cal.get(Calendar.YEAR);
                result = 2016;// 年份不再返回当前小时。而是返回2016年
                cal.get(Calendar.HOUR_OF_DAY);
                result = 7;// 小时不再返回当前小时。而是返回早上7点钟
            }
        };
        // 重放阶段
        Calendar now = Calendar.getInstance();
        Assert.assertEquals(2016, now.get(Calendar.YEAR));
        Assert.assertEquals(7, now.get(Calendar.HOUR_OF_DAY));
        // 验证阶段
        new Verifications() {
            {
                Calendar.getInstance();
                // 限定上面的方法只调用了1次，当然也可以不限定
                times = 1;
                cal.get(anyInt);
                // 限定上面的方法只调用了2次，当然也可以不限定
                times = 2;
            }
        };

    }
}
