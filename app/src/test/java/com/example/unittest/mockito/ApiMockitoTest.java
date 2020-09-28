package com.example.unittest.mockito;

import com.example.unittest.data.Api;
import com.example.unittest.data.Callback;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

/**
 * 测试类
 *
 * @author shouxianli on 2020/7/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ApiMockitoTest {

    @Spy
    private Api api;

    /**
     * Mock创建出来的对象字段使用JVM的初始值，方法体为空
     */
    @Mock
    private ArrayList<Object> mockList;
    /**
     * Spy创建的出的对象是真实实现
     */
    @Spy
    private ArrayList<Object> spyList;

    @Test
    public void test1() {
        Api api = new Api();
        Callback callback = Mockito.mock(Callback.class);
        api.register(callback);
        //mock对象才能verify‼️
        Mockito.verify(callback).registerSuccess();
    }

    /**
     * 修改方法返回值
     */
    @Test
    public void test1_1() {
        Mockito.doReturn("111", "222", "333")
                .doCallRealMethod()
                .when(api).getTel();
        System.out.println(api.getTel());
        System.out.println(api.getTel());
        System.out.println(api.getTel());
        System.out.println(api.getTel());
    }

    @Test
    public void test1_2() {
        Mockito.doReturn("1111").when(api).getTel();
        System.out.println(api.getTel());
    }

    @Test
    public void test1_3() {
        Mockito.when(api.getTel()).thenReturn("1111");
        System.out.println(api.getTel());
    }

    /**
     * 参数匹配器
     */
    @Test
    public void test2() {
//        Api api = new Api();//Mockito.mock(Api.class);
        Mockito.doReturn("Sam").when(api).getUserNameById(Mockito.anyInt(), Mockito.anyInt());
        Assert.assertEquals("Sam", api.getUserNameById(3, 4));
        Mockito.verify(api).getUserNameById(Mockito.eq(3), Mockito.eq(4));
    }

    /**
     * 自定义参数匹配器
     */
    @Test
    public void test3() throws Exception {
        Mockito.doReturn(true)
                .when(mockList).addAll(Mockito.argThat(new ListOfTwoElements()));

        boolean result1 = mockList.addAll(Arrays.asList("one", "two"));
        boolean result2 = mockList.addAll(Arrays.asList("one", "two", "three"));

        Assert.assertTrue(result1);
        Assert.assertTrue(!result2);
    }

    /**
     * 自定义的匹配器，用来测试list是否有且仅存在两个元素
     */
    class ListOfTwoElements implements ArgumentMatcher<List> {

        @Override
        public boolean matches(List list) {
            return list.size() == 2;
        }
    }

    /**
     * 验证调用次数
     */
    @Test
    public void test4() throws Exception {
        mockList.add("once");

        mockList.add("twice");
        mockList.add("twice");

        mockList.add("three times");
        mockList.add("three times");
        mockList.add("three times");

        Mockito.verify(mockList).add("once");  //验证mockList.add("once")调用了一次 - times(1) is used by default
        Mockito.verify(mockList, Mockito.times(1)).add("once");

        //调用多次校验
        Mockito.verify(mockList, Mockito.times(2)).add("twice");
        Mockito.verify(mockList, Mockito.times(3)).add("three times");

        //从未调用校验
        Mockito.verify(mockList, Mockito.never()).add("four times");

        //至少、至多调用校验
        Mockito.verify(mockList, Mockito.atLeastOnce()).add("three times");
        Mockito.verify(mockList, Mockito.atMost(5)).add("three times");
    }

    /**
     * 验证执行顺序
     */
    @Test
    public void test5() {
        List singleMock = Mockito.mock(List.class);

        singleMock.add("first add");
        singleMock.add("second add");

        InOrder inOrder = Mockito.inOrder(singleMock);

        //inOrder保证了方法的顺序执行
        inOrder.verify(singleMock).add("first add");
        inOrder.verify(singleMock).add("second add");

        List firstMock = Mockito.mock(List.class);
        List secondMock = Mockito.mock(List.class);

        firstMock.add("first add");
        firstMock.add("first add 2");
        secondMock.add("second add");

        //可以一次验证多个
        InOrder inOrder1 = Mockito.inOrder(firstMock, secondMock);

        /*
        只验证自己关心的方法即可
         */
        inOrder1.verify(firstMock).add("first add");
        inOrder1.verify(secondMock).add("second add");
    }

    /**
     * 确保mock对象未进行过unverify的交互
     *
     * verifyNoMoreInteractions 与 verifyZeroInteractions完全相同
     */
    @Test
    public void test6() throws Exception {
        mockList.add("one");

        Mockito.verify(mockList).add("one");
        Mockito.verify(mockList, Mockito.never()).add("two");

        //打开后测试失败。因为这里的add操作是unverify的
//        mockList.add("go");
        Mockito.verifyNoMoreInteractions(mockList);
//        Mockito.verifyZeroInteractions(mockList);
    }

    /**
     * 连续调用
     */
    @Test(expected = NullPointerException.class)
    public void test7() {
        Mockito.when(api.getUserNameById(Mockito.anyInt()))
                .thenReturn("John")
                .thenReturn("Jim", "God")
                .thenThrow(new NullPointerException("holy shit"));
//        Mockito.doReturn("John")
//                .doReturn("Jim", "God")
//                .doThrow(new NullPointerException("holy shit"))
//                .when(api).getUserNameById(Mockito.anyInt());
        //John
        System.out.println(api.getUserNameById(0));
        //Jim
        System.out.println(api.getUserNameById(0));
        //God
        System.out.println(api.getUserNameById(0));
        //抛出NPE
        System.out.println(api.getUserNameById(0));

    }

    /**
     * 操作返回值
     */
    @Test
    public void test8() throws Exception {
        Mockito.when(mockList.add(Mockito.anyString())).thenAnswer(new Answer<Boolean>() {

            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Object mock = invocation.getMock();
                return false;
            }
        });
        System.out.println(mockList.add("返回false"));

        /*
        then是thenAnswer的别名，两个方法一样
         */
        Mockito.when(mockList.add(Mockito.anyString())).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return true;
            }
        });
        System.out.println(mockList.add("返回true"));

        Mockito.when(mockList.add(Mockito.anyString())).thenReturn(false);
        System.out.println(mockList.add("返回false"));
    }

    /**
     * 拦截方法返回值 doReturn()、doThrow()、doAnswer()、doNothing()、doCallRealMethod()系列方法的运用
     */
    @Test(expected = RuntimeException.class)
    public void test9() throws Exception {
        //返回值为null的函数，可以通过这种方式进行测试
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                System.out.println("测试无返回值的函数");
                return null;
            }
        }).when(mockList).clear();

        Mockito.doThrow(new RuntimeException("测试无返回值的函数->抛出异常"))
                .when(mockList)
                .add(Mockito.eq(5), Mockito.anyString());

        Mockito.doNothing().when(mockList).add(Mockito.eq(2), Mockito.anyString());

        //不能把空返回值的函数与doReturn关联
//        doReturn("123456").when(mockList).add(eq(3), anyString());

        mockList.clear();
        mockList.add(2, "123");
        mockList.add(3, "123");
        mockList.add(4, "123");
        mockList.add(5, "123");

        //但是请记住这些add实际上什么都没有做，mock对象中仍然什么都没有
        System.out.print(mockList.get(4));
    }

    /**
     * 监控真实对象
     */
    @Test
    public void test10() throws Exception {
        //当spyList调用size()方法时，return100
        Mockito.when(spyList.size()).thenReturn(100);

        spyList.add("one");
        spyList.add("two");

        System.out.println("spyList第一个元素" + spyList.get(0));
        System.out.println("spyList.size = " + spyList.size());

        Mockito.verify(spyList).add("one");
        Mockito.verify(spyList).add("two");

        //请注意！下面这行代码会报错！ java.lang.IndexOutOfBoundsException: Index: 10, Size: 2
        // 因为当调用spy.get(10)时会调用真实对象的get(10)函数,此时会发生异常，因为真实List对象size是2
//        when(spyList.get(10)).thenReturn("ten");

        //应该这么使用
        Mockito.doReturn("ten").when(spyList).get(9);
        Mockito.doReturn("eleven").when(spyList).get(10);

        System.out.println("spyList第10个元素" + spyList.get(9));
        System.out.println("spyList第11个元素" + spyList.get(10));
    }

    @Captor
    public ArgumentCaptor<Student> captor;

    /**
     * 参数捕获器ArgumentCaptor
     */
    @Test
    public void test11() throws Exception {
        Student student = new Student();
        student.setName("qingmei2");
        Student student2 = new Student();
        student2.setName("qingmei3");

        //使用@Captor注解或者自行创建均可
//       captor = ArgumentCaptor.forClass(Student.class);
        mockList.add(student);
        mockList.add(student2);
        Mockito.verify(mockList, Mockito.atLeast(1))
                //捕获mockList调用add方法的参数
                .add(captor.capture());

        List<Student> students = captor.getAllValues();
        for (Student stu : students) {
            System.out.println(stu.getName());
        }
    }

    private class Student {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
