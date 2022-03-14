package com.example.unittest.jmockit;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import mockit.Delegate;
import mockit.Expectations;
import mockit.Invocation;
import org.junit.Assert;
import org.junit.Test;

/**
 * http://jmockit.cn/showArticle.htm?channel=2&id=4
 *
 * Expectations录制方法演示
 *
 * @author shouxianli on 2020/11/24.
 */
public class ApiJmockitTest {

    /**
     * 测试场景：当前是在中国
     */
    @Test
    public void testSayHelloAtChina() {
        // 假设当前位置是在中国
        new Expectations(Locale.class) {
            {
                Locale.getDefault();
                result = Locale.CHINA;
            }
        };
        // 断言说中文
        Assert.assertTrue("你好，JMockit!".equals((new HelloJMockit()).sayHello()));
    }

    /**
     * 测试场景：当前是在美国
     */
    @Test
    public void testSayHelloAtUS() {
        // 假设当前位置是在美国
        new Expectations(Locale.class) {
            {
                Locale.getDefault();
                result = Locale.US;
            }
        };
        // 断言说英文
        Assert.assertTrue("Hello，JMockit!".equals((new HelloJMockit()).sayHello()));
    }

    // 把类传入Expectations的构造函数
    @Test
    public void testRecordConstrutctor1() {
        Calendar cal = Calendar.getInstance();
        // 把待Mock的类传入Expectations的构造函数，可以达到只mock类的部分行为的目的
        new Expectations(Calendar.class) {
            {
                // 只对get方法并且参数为Calendar.HOUR_OF_DAY进行录制
                cal.get(Calendar.HOUR_OF_DAY);
                result = 7;// 小时永远返回早上7点钟
            }
        };
        Calendar now = Calendar.getInstance();
        // 因为下面的调用mock过了，小时永远返回7点钟了
        Assert.assertEquals(7, now.get(Calendar.HOUR_OF_DAY));
        // 因为下面的调用没有mock过，所以方法的行为不受mock影响，
        Assert.assertEquals(now.get(Calendar.DAY_OF_MONTH), (new Date()).getDate());
    }

    // 把对象传入Expectations的构造函数
    @Test
    public void testRecordConstrutctor2() {
        Calendar cal = Calendar.getInstance();
        // 把待Mock的对象传入Expectations的构造函数，可以达到只mock类的部分行为的目的，但只对这个对象影响
        new Expectations(cal) {
            {
                // 只对get方法并且参数为Calendar.HOUR_OF_DAY进行录制
                cal.get(Calendar.HOUR_OF_DAY);
                result = 7;// 小时永远返回早上7点钟
            }
        };

        // 因为下面的调用mock过了，小时永远返回7点钟了
        Assert.assertEquals(7, cal.get(Calendar.HOUR_OF_DAY));
        // 因为下面的调用没有mock过，所以方法的行为不受mock影响，
        Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH), (new Date()).getDate());

        // now是另一个对象，上面录制只对cal对象的影响，所以now的方法行为没有任何变化
        Calendar now = Calendar.getInstance();
        // 不受mock影响
        Assert.assertEquals(now.get(Calendar.HOUR_OF_DAY), (new Date()).getHours());
        // 不受mock影响
        Assert.assertEquals(now.get(Calendar.DAY_OF_MONTH), (new Date()).getDate());
    }

    /////////////同一方法返回时序结果

    // 一个类所有实例的某个方法，返回时序结果。
    // 适用场景：每次调用，期望返回的数据不一样。比如从tcp数据流中拿数据
    @Test
    public void testIfMethodOfClass() {
        AnOrdinaryClass instance = new AnOrdinaryClass();
        new Expectations(AnOrdinaryClass.class) {
            {
                instance.ordinaryMethod();
                // 对类AnOrdinaryClass所有实例调用ordinaryMethod方法时，依次返回1，2，3，4，5
                result = new int[]{1, 2, 3, 4, 5};
            }
        };
        AnOrdinaryClass instance1 = new AnOrdinaryClass();
        Assert.assertEquals(1, instance1.ordinaryMethod());
        Assert.assertEquals(2, instance1.ordinaryMethod());
        Assert.assertEquals(3, instance1.ordinaryMethod());
        Assert.assertEquals(4, instance1.ordinaryMethod());
        Assert.assertEquals(5, instance1.ordinaryMethod());
        // 因为在上面录制脚本中，只录制了5个结果，当大于5时，就以最后一次结果为准
        Assert.assertEquals(5, instance1.ordinaryMethod());
        Assert.assertEquals(5, instance1.ordinaryMethod());
    }

    // 与上述不一样的地方，仅仅是对某一个实例的返回值进行录制
    @Test
    public void testIfMethodOfIntance() {
        AnOrdinaryClass instance = new AnOrdinaryClass();
        new Expectations(instance) {
            {
                instance.ordinaryMethod();
                // 对实例instance调用ordinaryMethod方法时，依次返回1，2，3，4，5
                result = new int[]{1, 2, 3, 4, 5};
            }
        };
        // 只影响了instance这个实例
        Assert.assertEquals(1, instance.ordinaryMethod());
        Assert.assertEquals(2, instance.ordinaryMethod());
        Assert.assertEquals(3, instance.ordinaryMethod());
        Assert.assertEquals(4, instance.ordinaryMethod());
        Assert.assertEquals(5, instance.ordinaryMethod());
        // 因为在上面录制脚本中，只录制了5个结果，当大于5时，就以最后一次结果为准
        Assert.assertEquals(5, instance.ordinaryMethod());
        Assert.assertEquals(5, instance.ordinaryMethod());

        // 类AnOrdinaryClass的其它实例并不会受到影响
        AnOrdinaryClass instance1 = new AnOrdinaryClass();
        // ordinaryMethod这个方法本来就返回2
        Assert.assertEquals(2, instance1.ordinaryMethod());
        Assert.assertEquals(2, instance1.ordinaryMethod());
    }

    /////////////定制返回结果

    @Test
    public void testDelegate() {
        new Expectations(SayHello.class) {
            {
                SayHello instance = new SayHello();
                instance.sayHello(anyString, anyInt);
                result = new Delegate() {
                    // 当调用sayHello(anyString, anyInt)时，返回的结果就会匹配delegate方法，
                    // 方法名可以自定义，当入参和返回要与sayHello(anyString, anyInt)匹配上
                    String delegate(Invocation inv, String who, int gender) {
                        // 如果是向动物鹦鹉Polly问好，就说hello,Polly
                        if ("Polly".equals(who)) {
                            return "hello,Polly";
                        }
                        // 其它的入参，还是走原有的方法调用
                        return inv.proceed(who, gender);
                    }
                };
            }
        };

        SayHello instance = new SayHello();
        Assert.assertEquals("hello Mr david", instance.sayHello("david", ISayHello.MALE));
        Assert.assertEquals("hello Mrs lucy", instance.sayHello("lucy", ISayHello.FEMALE));
        Assert.assertEquals("hello,Polly", instance.sayHello("Polly", ISayHello.FEMALE));
    }
}
