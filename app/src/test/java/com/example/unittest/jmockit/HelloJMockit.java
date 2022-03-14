package com.example.unittest.jmockit;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author shouxianli on 2020/11/24.
 */
public class HelloJMockit {

    // 向JMockit打招呼
    public String sayHello() {
        Locale locale = Locale.getDefault();
        if (locale.equals(Locale.CHINA)) {
            // 在中国，就说中文
            return "你好，JMockit!";
        } else {
            // 在其它国家，就说英文
            return "Hello，JMockit!";
        }
    }
}

//打招呼的接口
interface ISayHello {

    // 性别：男
    int MALE = 0;
    // 性别：女
    int FEMALE = 1;

    /**
     * 打招呼
     *
     * @param who 向谁说
     * @param gender 对方的性别
     * @return 返回打招呼的内容
     */
    String sayHello(String who, int gender);

    /**
     * 向多个人打招呼
     *
     * @param who 向谁说
     * @param gender 对方的性别
     * @return 返回向多个人打招呼的内容
     */
    List<String> sayHello(String[] who, int[] gender);

}


class SayHello implements ISayHello {

    @Override
    public String sayHello(String who, int gender) {
        // 性别校验
        if (gender != FEMALE) {
            if (gender != MALE) {
                throw new IllegalArgumentException("illegal gender");
            }
        }
        // 根据不同性别，返回不同打招呼的内容
        switch (gender) {
            case FEMALE:
                return "hello Mrs " + who;
            case MALE:
                return "hello Mr " + who;
            default:
                return "hello  " + who;
        }
    }

    @Override
    public List<String> sayHello(String[] who, int[] gender) {
        // 参数校验
        if (who == null || gender == null) {
            return null;
        }
        if (who.length != gender.length) {
            throw new IllegalArgumentException();
        }
        //把向每个人打招呼的内容，保存到result中。
        List<String> result = new ArrayList<>();
        for (int i = 0; i < gender.length; i++) {
            result.add(this.sayHello(who[i], gender[i]));
        }
        return result;
    }

}