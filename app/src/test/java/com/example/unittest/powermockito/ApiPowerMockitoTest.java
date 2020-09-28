package com.example.unittest.powermockito;

import com.example.unittest.data.Api;
import com.example.unittest.data.Dao;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.Stubber;
import org.powermock.api.support.SuppressCode;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * 测试类
 *
 * @author shouxianli on 2020/7/17.
 */
@RunWith(PowerMockRunner.class)
//该注解标注到类或者方法上
@PrepareForTest({Api.class, Dao.class})
public class ApiPowerMockitoTest {

    private Api api;

    @Before
    public void setup() {
        api = new Api();
    }

    /**
     * 操作私有属性 Whitebox就是一个反射操作类
     *
     * 其他方法参考源文件即可
     */
    @Test
    public void test1() {
        //设置私有属性
        Whitebox.setInternalState(api, "name", "Sam");
        //读取私有属性
        String name = Whitebox.getInternalState(api, "name");
        Assert.assertEquals("Sam", name);
    }

    /**
     * Verify私有对象
     */
    @Test
    public void test2() throws Exception {
        Api mockApi = PowerMockito.spy(api);
        mockApi.login();
        PowerMockito.verifyPrivate(mockApi, Mockito.times(1))
                .invoke("checkName", Mockito.isNull());
    }

    @Test
    public void test2_1() {
        Api mockApi = PowerMockito.spy(api);
        mockApi.realLogin();
        Mockito.verify(mockApi).onRealLogin();
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * 替换new出来的对象
     * 无法替换匿名类‼️
     */
    @Test
//    @Test(expected = NullPointerException.class)
    public void test3() throws Exception {
        Dao mockDao = Mockito.mock(Dao.class);
        PowerMockito.whenNew(Dao.class)
                .withNoArguments()
                //也支持连续调用
                .thenReturn(mockDao)
                .thenThrow(new NullPointerException("null"));
        //login中会创建Dao，并调用save方法
        api.realLogin();
        Mockito.verify(mockDao).save();

        System.out.println("再次调用就崩了");
        expectedException.expect(NullPointerException.class);
        api.realLogin();
    }

    /**
     * 跳过不执行某个方法 Mockito中有doNothing，PowerMockito有其他实现
     */
    @Test
    public void test4() throws Exception {
        SuppressCode.suppressMethod(Whitebox.getMethod(Api.class, "checkName", String.class));
        /*
        注意这里的打印信息！
        checkName作为if的判断条件，在suppress后，判断条件直接是false
         */
        api.login();
    }

    /**
     * suppress的方法不可verify
     *
     * @throws Exception
     */
    @Test
    public void test4_1() throws Exception {
        SuppressCode.suppressMethod(Whitebox.getMethod(Api.class, "checkName", String.class));

        Api mockApi = PowerMockito.spy(api);
//        mockApi.login();
        PowerMockito.verifyPrivate(mockApi)
                .invoke("checkName", Mockito.isNull());
    }

    /**
     * suppress filed
     */
    @Test
    public void test4_2() {
        SuppressCode.suppressField(Api.class, "name");
        api.setName("111");
        System.out.println(api.getName());
    }

    /**
     * suppress 构造方法
     */
    @Test
    public void test4_3() {
        SuppressCode.suppressConstructor(Dao.class);
        Dao dao = new Dao();
        System.out.println(dao);
    }

    /**
     * 替换private/static/final方法的实现
     *
     * 通过修改字节码实现
     */
    @Test
    public void test5() throws Exception {
        PowerMockito.replace(Whitebox.getMethod(Api.class, "checkName", String.class))
                .with(new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args)
                            throws Throwable {
                        System.out.println("checkname, param is " + args[0]);
                        return false;
                    }
                });
        api.setName("Sollian");
        api.login();
    }

    @Test
    public void test5_1() {
        PowerMockito.replace(Whitebox.getMethod(Api.class, "genKey", String.class, long.class))
                .with(Whitebox.getMethod(getClass(), "replaceGenKey", String.class, long.class));
        System.out.println(Api.genKey("aaa", 1000));
    }

    public static String replaceGenKey(String name, long time) {
        System.out.println("replace, param is " + name + "," + time);
        return name;
    }

    /**
     * 修改private/final方法的返回值
     */
    @Test
    public void test6() throws Exception {
        Api mockApi = PowerMockito.mock(Api.class);
//        PowerMockito.doReturn(false)
//                .when(mockApi, "checkName", Mockito.anyString());
        PowerMockito.when(mockApi, "checkName", Mockito.anyString())
                .thenReturn(false);
        PowerMockito.doCallRealMethod().when(mockApi).login();
        mockApi.login();
    }

    @Test
    public void test6_1() throws Exception {
        Api mockApi = PowerMockito.spy(new Api());
        PowerMockito.doReturn(false)
                .when(mockApi, "checkName",
                        /*
                        注意使用spy时，这里一定要写成any，而不是anyString!!!，
                        因为login在调用checkName时，传入的成员变量name，值为null

                        参数也可以是null、Mockito.isNull()
                         */
                        Mockito.any());
        mockApi.login();
    }

    /**
     * 修改static方法返回值
     */
    @Test
    public void test7() throws Exception {
        PowerMockito.mockStatic(Api.class);
        PowerMockito.doReturn("wahaha")
                .when(Api.class, "genKey", Mockito.anyString(), Mockito.anyLong());
        System.out.println(Api.genKey("Amy", 100L));

        //验证static方法调用
        PowerMockito.verifyStatic(Api.class, Mockito.times(1));
        //注意参数的设置，要么都用确定值，要么都用匹配器
//        Api.genKey("Amy", 100);
        Api.genKey(Mockito.anyString(), Mockito.eq(100L));//结尾的L不能省略
    }

    @Test
    public void test7_1() throws Exception {
        PowerMockito.mockStatic(Api.class);
        PowerMockito.doReturn("wahaha")
                .when(Api.class, "genKeyInner", Mockito.anyString(), Mockito.anyLong());

        System.out.println((String) Whitebox.invokeMethod(Api.class, "genKeyInner", "Amy", 100L));

        //验证private static方法调用
        PowerMockito.verifyPrivate(Api.class)
                .invoke("genKeyInner", Mockito.anyString(), Mockito.anyLong());
    }

    @Test
    public void test7_2() throws Exception {
        PowerMockito.spy(Api.class);
        PowerMockito.doReturn("wahaha")
                .when(Api.class, "getCacheById", Mockito.any());
        System.out.println(Api.getCache("111"));
    }

    @Test
    public void test8() {
        PowerMockito.stub(Whitebox.getMethod(Api.class, "getCacheById", String.class))
                .toReturn("wahaha");
        System.out.println(Api.getCache("111"));
    }

    @Test(expected = Exception.class)
    public void test8_1() {
        PowerMockito.stub(Whitebox.getMethod(Dao.class, "save"))
                .toThrow(new Exception("111"));
        api.realLogin();
    }

    @Test
    public void test9() {
        Stubber.stubMethod(Whitebox.getMethod(Api.class, "getCacheById", String.class),
                "wahaha");
        System.out.println(Api.getCache("111"));
    }

    @Test
    public void test9_1() {
        Stubber.stubMethod(Whitebox.getMethod(Dao.class, "delete"), true);
        System.out.println(api.deleteUser());
    }
}
