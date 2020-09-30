package com.example.unittest.espresso;

import android.app.Activity;
import android.app.Instrumentation.ActivityResult;
import android.content.Intent;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.unittest.MainActivity;
import com.example.unittest.R;
import com.example.unittest.WebViewActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * 测试Intent相关
 * Created by shihao on 2017/3/2.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest2 {

    @Rule
    public IntentsTestRule activityTestRule = new IntentsTestRule(MainActivity.class);

    @Test
    public void testWeb() {
        Espresso.onView(ViewMatchers.withId(R.id.go_web))
                .perform(ViewActions.click());
        Intents.intended(IntentMatchers.hasExtra(WebViewActivity.EXTRA_URL, "https://m.baidu.com"));
    }

    /**
     * 测试onActivityResult
     */
    @Test
    public void testResult() {
        Intent intent = new Intent();
        intent.putExtra("data", "hello world");
        ActivityResult activityResult = new ActivityResult(Activity.RESULT_OK, intent);

        Intents.intending(IntentMatchers.hasComponent("com.example.unittest.RecycleviewActivity"))
                .respondWith(activityResult);

        //会调用startForResult
        Espresso.onView(ViewMatchers.withId(R.id.go_list))
                .perform(ViewActions.click());
        //onActivityResult调用会修改sum元素的显示
        Espresso.onView(ViewMatchers.withId(R.id.sum))
                .check(ViewAssertions.matches(ViewMatchers.withText("hello world")));
    }
}