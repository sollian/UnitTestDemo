package com.example.unittest.espresso;


import android.content.Intent;
import androidx.test.espresso.web.assertion.WebViewAssertions;
import androidx.test.espresso.web.sugar.Web;
import androidx.test.espresso.web.webdriver.DriverAtoms;
import androidx.test.espresso.web.webdriver.Locator;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.example.unittest.WebViewActivity;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by shihao on 2017/3/3.
 */
@RunWith(AndroidJUnit4.class)
public class WebViewActivityTest {

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(WebViewActivity.class, false);

    @Ignore
    @Test
    public void test() {
        //传递数据到WebViewActivity
        Intent intent = new Intent();
        intent.putExtra(WebViewActivity.EXTRA_URL, "https://m.baidu.com");
        activityTestRule.launchActivity(intent);

        //通过name为"word"找到搜索输入框
        Web.onWebView().withElement(DriverAtoms.findElement(Locator.NAME, "word"))
                //往输入框中输入字符串"android"
                .perform(DriverAtoms.webKeys("android"))
                //通过id为"index-bn"找到"百度一下"按钮
                .withElement(DriverAtoms.findElement(Locator.ID, "index-bn"))
                //执行点击事件
                .perform(DriverAtoms.webClick())
                //通过id为"results"找到结果div
                .withElement(DriverAtoms.findElement(Locator.ID, "results"))
                //检查div中是否包含字符串"android"
                .check(WebViewAssertions
                        .webMatches(DriverAtoms.getText(), Matchers.containsString("android")));

    }

}
