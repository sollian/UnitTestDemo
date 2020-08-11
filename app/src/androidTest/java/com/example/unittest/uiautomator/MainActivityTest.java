package com.example.unittest.uiautomator;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SdkSuppress;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author shouxianli on 2020/8/10.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class MainActivityTest {

    private static final String PKG_NAME = "com.example.unittest";
    private static final int TIME_OUT = 5000;

    private Instrumentation instrumentation;
    private UiDevice uiDevice;

    @Before
    public void setup() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        uiDevice = UiDevice.getInstance(instrumentation);

        startActivity();
    }

    private void startActivity() {
        Context context = instrumentation.getContext();
        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(PKG_NAME);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        //等待启动完毕
        uiDevice.wait(Until.hasObject(By.pkg(PKG_NAME).depth(0)), TIME_OUT);
    }

    @Test
    public void testAdd() throws Exception {
        uiDevice.findObject(new UiSelector().resourceId("com.example.unittest:id/editText")).setText("2");
        uiDevice.findObject(By.res("com.example.unittest:id/editText2")).setText("5");
        uiDevice.findObject(By.res(PKG_NAME, "button")).click();
        Assert.assertEquals("计算结果：7",
                uiDevice.findObject(new UiSelector().resourceId("com.example.unittest:id/textView")).getText());
    }

    @Test
    public void testListView() throws Exception {
        UiScrollable list = new UiScrollable(new UiSelector().resourceId("com.example.unittest:id/list"));
        list.getChildByText(new UiSelector().className("android.widget.TextView"), "20")
                .click();
        Assert.assertEquals("20",
                uiDevice.findObject(new UiSelector().resourceId("com.example.unittest:id/list_result")).getText());
    }

    @Test
    public void testRecyclerView() throws Exception {
        //启动新页面，需要等待
        uiDevice.findObject(By.text("RecycleView")).clickAndWait(Until.newWindow(), TIME_OUT);
        //或者使用下面的方式
//        uiDevice.findObject(By.text("RecycleView")).click();
//        uiDevice.wait(Until.hasObject(By.text("Item 0")), TIME_OUT);

        //显示dislog需要等待
        uiDevice.findObject(new UiSelector().text("Item 0")).clickAndWaitForNewWindow();
        //或者使用下面的方式
//        uiDevice.findObject(new UiSelector().text("Item 0")).click();
//        uiDevice.wait(Until.hasObject(By.text("确定")), TIME_OUT);

        uiDevice.findObject(By.text("确定")).click();
    }
}