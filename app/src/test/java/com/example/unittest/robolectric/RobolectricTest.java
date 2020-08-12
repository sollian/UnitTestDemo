package com.example.unittest.robolectric;

import android.content.Intent;
import com.example.unittest.AboutActivity;
import com.example.unittest.MainActivity;
import com.example.unittest.MyBroadcastReceiver;
import com.example.unittest.MyService;
import com.example.unittest.R;
import com.example.unittest.util.MySharePreference;
import org.junit.Assert;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowToast;

/**
 * @author shouxianli on 2020/7/17.
 */
public class RobolectricTest extends BaseRobolectricTest {

    /**
     * 测试sp
     */
    @Test
    public void test1() {
        MySharePreference sp = new MySharePreference();
        sp.put("data", "sss");
        Assert.assertEquals("sss", sp.get("data"));
    }

    /**
     * activity
     */
    @Test
    public void test2() {
        ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class);
        MainActivity mainActivity = controller.get();
        controller.create();
        controller.start();
        controller.resume();

        /*
        测试activity跳转
         */
        mainActivity.findViewById(R.id.go_about).performClick();
        // 获取对应的Shadow类
        ShadowActivity shadowActivity = Shadows.shadowOf(mainActivity);
        // 借助Shadow类获取启动下一Activity的Intent
        Intent nextIntent = shadowActivity.getNextStartedActivity();
        // 校验Intent的正确性
        Assert.assertEquals(AboutActivity.class.getName(),
                nextIntent.getComponent().getClassName());
        Assert.assertEquals(123456, nextIntent.getIntExtra("time", 0));
        String name = getContext().getResources().getString(R.string.app_name);
        System.out.println("app_name:" + name);

        /*
        测试toast
         */
        mainActivity.findViewById(R.id.toast).performClick();
        Assert.assertEquals("hello", ShadowToast.getTextOfLatestToast());
    }

    /**
     * broadcastReceiver
     */
    @Test
    public void test3() {
        ShadowApplication shadowApplication = Shadows.shadowOf(getApplication());
        Intent intent = new Intent();
        intent.setAction("com.broadcast.static");
        intent.putExtra("test", "test data 123");

        //测试是否注册广播接收者
        Assert.assertTrue(shadowApplication.hasReceiverForIntent(intent));
        //或者
//        List<ResolveInfo> list = getContext().getPackageManager()
//                .queryBroadcastReceivers(intent, PackageManager.GET_RESOLVED_FILTER);
//        Assert.assertTrue(!list.isEmpty());

        //广播接受者的处理逻辑是否正确
        MyBroadcastReceiver receiver = new MyBroadcastReceiver();
        receiver.onReceive(getApplication(), intent);

        Assert.assertEquals("test data 123", receiver.getMsg());
    }

    /**
     * service
     */
    @Test
    public void test4() {

        Intent intent = new Intent(getApplication(), MyService.class);
        intent.putExtra("test", "数据传输");

        MyService serviceController = Robolectric.setupService(MyService.class);
        serviceController.onStartCommand(intent, 0, 1);

        Assert.assertEquals("数据传输", serviceController.getMsg());
    }
}
