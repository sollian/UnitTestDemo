/**
 * 更强大的测试框架
 * 原理：
 * 基于cglib创建动态代理对象，后续的验证、修改操作，均依赖此对象
 *
 * cglib简介：
 * 与java的Proxy动态代理不同，cglib是通过继承方式来实现的，所以不依赖接口
 * 但是受限于继承机制的特性，不能代理final类，不能代理final、private、static方法
 *
 * 相对JUnit的优势：
 * 1、验证方法是否调用，及调用次数、参数、顺序等
 * 2、修改方法行为，包括返回值、异常等
 */

package com.example.unittest.mockito;