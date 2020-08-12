package com.example.unittest.PowerMockito;

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
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * @author shouxianli on 2020/7/17.
 */
@RunWith(PowerMockRunner.class)
//该注解标注到类或者方法上
@PrepareForTest(Api.class)
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

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * 替换new出来的对象
     * 无法替换匿名类‼️
     */
    @Test
//    @Test(expected = NullPointerException.class)
    public void test3() throws Exception {
        Dao dao = new Dao();
        Dao mockDao = PowerMockito.spy(dao);
        PowerMockito.whenNew(Dao.class)
                .withNoArguments()
                //也支持连续调用
                .thenReturn(mockDao)
                .thenThrow(new NullPointerException("null"));
        //login中会创建Dao，并调用save方法
        api.login();
        Mockito.verify(mockDao).save();

        System.out.println("再次调用就崩了");
        expectedException.expect(NullPointerException.class);
        api.login();
    }

    /**
     * 跳过不执行某个方法 Mockito中有doNothing，PowerMockito有其他实现
     */
    @Test
    public void test4() throws Exception {
        PowerMockito.suppress(Whitebox.getMethod(Api.class, "checkName", String.class));
        /*
        注意这里的打印信息！
        checkName作为if的判断条件，在suppress后，判断条件直接是false
         */
        api.login();

        /*
        suppress checkName后，如果再verify这个方法就会测试失败
         */
//        Api mockApi = PowerMockito.spy(api);
//        mockApi.login();
//        PowerMockito.verifyPrivate(mockApi)
//                .invoke("checkName", Mockito.isNull());
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
                        System.out.println(args[0]);
                        return false;
                    }
                });
        api.setName("Sollian");
        api.login();
    }

    /**
     * 修改private/final方法的返回值
     */
    @Test
    public void test6() throws Exception {
        Api mockApi = PowerMockito.mock(Api.class);
        //spy是不可以的！！
//        Api mockApi = PowerMockito.spy(api);

        PowerMockito.when(mockApi, Whitebox.getMethod(Api.class, "checkName", String.class))
                .withArguments(Mockito.anyString())
                .thenReturn(false);
        //或者向下面这样调用
//        PowerMockito.when(mockApi, "checkName", Mockito.anyString())
//                .thenReturn(false);

        PowerMockito.doCallRealMethod().when(mockApi).login();
        mockApi.login();
    }

    /**
     * 修改static方法返回值
     */
    @Test
    public void test7() throws Exception {
        System.out.println(Api.genKey("Amy", 100));

        PowerMockito.mockStatic(Api.class);
        PowerMockito.doReturn("wahaha")
                .when(Api.class, "genKey", Mockito.anyString(), Mockito.anyLong());
        //或者向下面这样调用
//        PowerMockito.when(Api.genKey(Mockito.anyString(), Mockito.anyLong()))
//                .thenReturn("wahaha");
        System.out.println(Api.genKey("Amy", 100));

        //验证static方法调用
        PowerMockito.verifyStatic(Api.class, Mockito.times(1));
        //注意参数的设置，要么都用确定值，要么都用匹配器
//        Api.genKey("Amy", 100);
        Api.genKey(Mockito.anyString(), Mockito.eq(100L));//结尾的L不能省略
    }
}
