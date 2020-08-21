package com.example.unittest.data;

/**
 * 测试类
 *
 * @author shouxianli on 2020/7/15.
 */
public class Api {

    private String name;

    public static String genKey(String name, long time) {
        return name + '_' + time;
    }

    public void create() {
        System.out.println("初始化api资源");
    }

    public void connect() {
        System.out.println("建立连接");
    }

    public void closeConnection() {
        System.out.println("断开连接");
    }

    public void destroy() {
        System.out.println("关闭所有api资源");
    }

    public void exception() {
        throw new NullPointerException("空指针了");
    }

    public void exception2(int error) {
        switch (error) {
            case 0:
                break;
            case 1:
                throw new IllegalArgumentException("用户名错误");
            case 2:
                throw new IllegalArgumentException("密码错误");
            case 3:
                throw new NullPointerException("用户名错误");
            default:
                break;
        }
    }

    public void timeout() throws InterruptedException {
        Thread.sleep(500);
    }

    public int add(int a, int b) {
        return a + b;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void login() {
        if (checkName(name)) {
            System.out.println("login success");
        } else {
            System.out.println("invalid name");
        }

        new Dao().save();
    }

    private final boolean checkName(String name) {
        System.out.println("call checkName");
        return true;
    }

    public String getUserNameById(int id) {
        return "Jimy";
    }


}
