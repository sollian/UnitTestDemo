package com.example.unittest.jmockit;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.Assert;
import org.junit.Test;

/**
 * Mockup&@Mock Verifications演示
 *
 * @author shouxianli on 2020/11/24.
 */
public class ApiJmockitTest5 {

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
