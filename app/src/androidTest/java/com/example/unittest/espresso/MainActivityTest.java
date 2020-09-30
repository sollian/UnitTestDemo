package com.example.unittest.espresso;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.web.assertion.WebViewAssertions;
import androidx.test.espresso.web.sugar.Web;
import androidx.test.espresso.web.webdriver.DriverAtoms;
import androidx.test.espresso.web.webdriver.Locator;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.example.unittest.MainActivity;
import com.example.unittest.R;
import java.util.Map;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 测试类
 * Created by shouxianli on 2020/7/29.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    /**
     * 通过使用ActivityTestRule，
     * 测试框架在每个使用@Test注释的测试方法之前以及在使用@Before注释的方法之前启动被测Activity。
     * 测试完成后，框架处理关闭活动，所有使用@After注释的方法都会运行。
     */
    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void testAdd() {
        //通过id找到edittext，在里面输入2并关闭输入法
        Espresso.onView(ViewMatchers.withId(R.id.num1))
                .perform(ViewActions.typeText("2"),
                        ViewActions.closeSoftKeyboard());
        //通过id找到edittext，在里面输入5并关闭输入法
        Espresso.onView(ViewMatchers.withId(R.id.num2))
                .perform(ViewActions.typeText("5"),
                        ViewActions.closeSoftKeyboard());
        //通过id找到button，执行点击事件
        Espresso.onView(ViewMatchers.withId(R.id.calculate))
                .perform(ViewActions.click());
        //通过id找到textview，并判断是否与文本匹配
        Espresso.onView(ViewMatchers.withId(R.id.sum))
                .check(ViewAssertions.matches(ViewMatchers.withText("计算结果：7")));
    }

    @Test
    public void testListView() {
        //AdapterView使用onData方法匹配
        Espresso.onData(
                Matchers.allOf(
                        //item对应的数据类型是Map
                        Matchers.instanceOf(Map.class),
                        //定位到map的value是50的那个item
                        Matchers.hasValue("50")
                )
        ).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.list_result))
                .check(ViewAssertions.matches(ViewMatchers.withText("50")));
    }

    @Test
    public void testRecycleView() {
        //通过文本RecycleView找到按钮，并执行点击事件，跳转到RecycleviewActivity
        Espresso.onView(ViewMatchers.withText("RecycleView"))
                .perform(ViewActions.click());
        //通过文本"Item 0"找到view，并检查是否显示，然后执行点击事件，此时会弹出对话框
        Espresso.onView(ViewMatchers.withText("Item 0"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click());
        //通过文本"确定"找到对话框上的确定按钮，执行点击事件，关闭对话框
        Espresso.onView(ViewMatchers.withText("确定"))
                .perform(ViewActions.click());
        //通过文本"Item 2"找到view，并检查是否显示，然后执行点击事件，此时会弹出对话框
        Espresso.onView(ViewMatchers.withText("Item 2"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click());
        //执行点击返回按钮事件，关闭对话框
        Espresso.pressBack();

        //通过id找到recycleview，然后执行滑动事件，滑动到21项
        Espresso.onView(ViewMatchers.withId(R.id.recycleview))
                .perform(RecyclerViewActions.scrollToPosition(21));
        //通过文本"Item 20"找到view，并检查是否显示，然后执行点击事件，此时会弹出对话框
        Espresso.onView(ViewMatchers.withText("Item 20"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click());
        //通过文本"确定"找到对话框上的确定按钮，执行点击事件，关闭对话框
        Espresso.onView(ViewMatchers.withText("确定"))
                .perform(ViewActions.click());
        //执行点击返回按钮事件，回到MainActivity
        Espresso.pressBack();
    }

    @Ignore
    @Test
    public void testWebView() {
        //通过文本RecycleView找到按钮，并执行点击事件，跳转到WebViewActivity
        Espresso.onView(ViewMatchers.withText("WebView"))
                .perform(ViewActions.click());
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
        //执行点击返回按钮事件，关闭跳转到WebViewActivity
        Espresso.pressBack();
    }

}