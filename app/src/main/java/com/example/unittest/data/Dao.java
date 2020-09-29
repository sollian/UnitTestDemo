package com.example.unittest.data;

/**
 * 测试类
 *
 * @author shouxianli on 2020/7/17.
 */
public class Dao {

    public static String LIB = "test_lib";

    static {
        System.out.println("init Dao");
    }

    private int id;

    public Dao() {
        id = 11;
    }

    public void save() {
        System.out.println("save");
    }

    public boolean delete() {
        return true;
    }

    @Override
    public String toString() {
        return "Dao{" +
                "id=" + id +
                '}';
    }
}
