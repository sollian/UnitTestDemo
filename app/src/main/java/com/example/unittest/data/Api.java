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

    private static String genKeyInner(String name, long time) {
        return name + '_' + time;
    }

    public static String getCache(String key) {
        System.out.println("getCache");
        return "prefix_" + getCacheById(key);
    }

    public static String getCacheById(String key) {
        System.out.println("getCacheById");
        return key;
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

    public int minus(int a, int b) {
        return a - b;
    }

    public String getName() {
        System.out.println("getName, filed name is " + name);
        return name;
    }

    public void setName(String name) {
        System.out.println("setName, param is " + name);
        this.name = name;
        System.out.println("setName, field name is " + this.name);
    }

    public void login() {
        if (checkName(name)) {
            System.out.println("login success");
        } else {
            System.out.println("invalid name");
        }
    }

    private final boolean checkName(String name) {
        System.out.println("call checkName");
        return true;
    }

    public void register(Callback callback) {
        if (callback != null) {
            callback.registerSuccess();
        }
    }

    public void realLogin() {
        onRealLogin();
        new Dao().save();
    }

    public void onRealLogin() {
    }

    public boolean deleteUser() {
        return new Dao().delete();
    }

    public String getUserNameById(int id) {
        return "Jimy";
    }

    public String getUserNameById(int id, int index) {
        return "Jimy";
    }

    public String getTel() {
        System.out.println("getTel");
        return "123456";
    }
}
