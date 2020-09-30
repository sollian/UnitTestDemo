package com.example.unittest.espresso;

import android.content.Intent;
import android.os.IBinder;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ServiceTestRule;
import com.example.unittest.MyService;
import java.util.concurrent.TimeoutException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 测试类
 *
 * @author shouxianli on 2020/9/29.
 */
@RunWith(AndroidJUnit4.class)
public class ServiceTest {

    @Rule
    public ServiceTestRule serviceTestRule = new ServiceTestRule();

    @Test
    public void test() throws TimeoutException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MyService.class);
        intent.putExtra("test", "数据传输");

        IBinder binder = serviceTestRule.bindService(intent);
        MyService service = ((MyService.MyBinder) binder).getService();

        Assert.assertEquals("数据传输", service.getMsg());
    }
}
