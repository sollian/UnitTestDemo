package com.example.unittest.uiautomator;

import android.app.Instrumentation;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiSelector;
import com.example.unittest.MainActivity;
import com.example.unittest.R;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 组合使用Espresso和uiatomator
 *
 * @author shouxianli on 2020/8/10.
 */
@RunWith(AndroidJUnit4.class)
public class ComposeTest {

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(MainActivity.class);

    private static final String PKG_NAME = "com.example.unittest";
    private static final int TIME_OUT = 5000;

    private Instrumentation instrumentation;
    private UiDevice uiDevice;

    @Before
    public void setup() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        uiDevice = UiDevice.getInstance(instrumentation);
    }

    @Test
    public void testAdd() throws Exception {
        uiDevice.findObject(new UiSelector().resourceId("com.example.unittest:id/editText")).setText("2");
        uiDevice.findObject(By.res("com.example.unittest:id/editText2")).setText("5");
        uiDevice.findObject(By.res(PKG_NAME, "button")).click();
        Espresso.onView(ViewMatchers.withId(R.id.textView))
                .check(ViewAssertions.matches(ViewMatchers.withText("计算结果：7")));
    }
}
