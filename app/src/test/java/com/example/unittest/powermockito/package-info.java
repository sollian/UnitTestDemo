/**
 * 简介：
 * 扩展Mockito或者EasyMock的测试框架
 * 可以操作final类，private/static/final/构造方法，字段等
 *
 * 原理：
 * 依赖javassist和objenesis（绕过构造方法实例化一个类），
 * 修改字节码来mock private/static/final等方法
 * 对于系统类，PowerMockito不会修改，只会修改调用处的代码
 *
 * 注意事项：
 * 1、使用自定义的MockClassLoader加载测试用例中用到的类，如遇到类加载错误，
 * 考虑使用@PowerMockIgnore注解来让系统ClassLoader加载类
 * 2、PowerMockito 和Mockito mock出来的对象不能相互使用（这个有待商讨）
 * 3、注意和Mockito版本的对应。否则可能出现org.mockito.exceptions.misusing.NotAMockException的问题‼️
 */

package com.example.unittest.powermockito;