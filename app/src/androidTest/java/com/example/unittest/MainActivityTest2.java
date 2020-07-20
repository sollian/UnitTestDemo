package com.example.unittest;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.web.assertion.WebViewAssertions;
import androidx.test.espresso.web.sugar.Web;
import androidx.test.espresso.web.webdriver.DriverAtoms;
import androidx.test.espresso.web.webdriver.Locator;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Created by shihao on 2017/3/2.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest2 {

    @Rule
    public IntentsTestRule activityTestRule = new IntentsTestRule(MainActivity.class);

    @Test
    public void test() {
        Espresso.onView(ViewMatchers.withId(R.id.button3))
                .perform(ViewActions.click());
        Intents.intended(IntentMatchers.hasExtra(WebViewActivity.EXTRA_URL, "https://m.baidu.com"));
    }
}