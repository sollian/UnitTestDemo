package com.example.unittest.uiautomator;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SdkSuppress;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;
import com.example.unittest.MainActivity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 测试类
 *
 * @author shouxianli on 2020/8/10.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class MainActivityTest {

    private static final String PKG_NAME = "com.example.unittest";
    private static final int TIME_OUT = 5000;

    private UiDevice uiDevice;

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(MainActivity.class);

    @Before
    public void setup() {
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void testAdd() throws Exception {
        uiDevice.findObject(new UiSelector().resourceId("com.example.unittest:id/num1")).setText("2");
        uiDevice.findObject(By.res("com.example.unittest:id/num2")).setText("5");
        uiDevice.findObject(By.res(PKG_NAME, "calculate")).click();
        Assert.assertEquals("计算结果：7",
                uiDevice.findObject(new UiSelector().resourceId("com.example.unittest:id/sum")).getText());
    }

    @Test
    public void testListView() throws Exception {
        UiScrollable list = new UiScrollable(new UiSelector().resourceId("com.example.unittest:id/list"));
        list.getChildByText(new UiSelector().className("android.widget.TextView"), "5")
                .click();
        Assert.assertEquals("5",
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