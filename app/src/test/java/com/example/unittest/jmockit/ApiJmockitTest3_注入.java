package com.example.unittest.jmockit;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Tested与@Injectable搭配使用
 */
public class ApiJmockitTest3_注入 {

    //@Tested修饰的类，表示是我们要测试对象,在这里表示，我想测试订单服务类。JMockit也会帮我们实例化这个测试对象
    @Tested
    private OrderService orderService;
    //测试用户ID
    private long testUserId = 123456l;
    //测试商品id
    private long testItemId = 456789l;

    // 测试注入方式
    @Test
    public void testSubmitOrder(
            @Injectable MailService mailService,
            @Injectable UserCheckService userCheckService
    ) {
        new Expectations() {
            {
                // 当向testUserId发邮件时，假设都发成功了
                mailService.sendMail(testUserId, anyString);
                result = true;
                // 当检验testUserId的身份时，假设该用户都是合法的
                userCheckService.check(testUserId);
                result = true;
            }
        };
        // JMockit帮我们实例化了mailService了，并通过OrderService的构造函数，注入到orderService对象中。
        //JMockit帮我们实例化了userCheckService了，并通过OrderService的属性，注入到orderService对象中。
        Assert.assertTrue(orderService.submitOrder(testUserId, testItemId));
        Assert.assertEquals("10", orderService.id);
    }
}

interface MailService {

    boolean sendMail(long userId, String content);
}

interface UserCheckService {

    boolean check(long userId);
}

class OrderService {

    MailService mailService;
    UserCheckService userCheckService;

    String id = "10";

    public OrderService(MailService mailService) {
        this.mailService = mailService;
    }

    public boolean submitOrder(long buyerId, long itemId) {
        if (!userCheckService.check(buyerId)) {
            System.out.println("userCheckService 校验失败");
            return false;
        }
        if (!mailService.sendMail(buyerId, "下单成功")) {
            System.out.println("mailService 发送邮件失败");
            return false;
        }
        return true;
    }
}