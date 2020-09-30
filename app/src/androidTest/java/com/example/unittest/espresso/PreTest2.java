package com.example.unittest.espresso;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ActivityScenario.ActivityAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.unittest.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 测试类
 *
 * @author shouxianli on 2020/9/29.
 */
@RunWith(AndroidJUnit4.class)
public class PreTest2 {

    @Rule
    public ActivityScenarioRule activityTestRule = new ActivityScenarioRule(MainActivity.class);

    @Test
    public void testThread2() {
        ActivityScenario activityScenario = activityTestRule.getScenario();
        Log.e("---测试线程", Thread.currentThread().getName());
        activityScenario.onActivity(new ActivityAction() {
            @Override
            public void perform(Activity activity) {
                Toast.makeText(activity, "information", Toast.LENGTH_SHORT).show();
                Log.e("---主线程", Thread.currentThread().getName());
            }
        });
    }
}
