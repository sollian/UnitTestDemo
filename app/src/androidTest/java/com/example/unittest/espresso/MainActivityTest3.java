package com.example.unittest.espresso;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import com.example.unittest.MainActivity;
import com.example.unittest.R;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest3 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void mainActivityTest2() {
        ViewInteraction appCompatEditText = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.editText),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.activity_main),
                                        childAtPosition(
                                                ViewMatchers.withClassName(Matchers.is("android.widget.LinearLayout")),
                                                3)),
                                0),
                        ViewMatchers.isDisplayed()));
        appCompatEditText.perform(ViewActions.replaceText("7"), ViewActions.closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.editText2),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.activity_main),
                                        childAtPosition(
                                                ViewMatchers.withClassName(Matchers.is("android.widget.LinearLayout")),
                                                3)),
                                1),
                        ViewMatchers.isDisplayed()));
        appCompatEditText2.perform(ViewActions.replaceText("8"), ViewActions.closeSoftKeyboard());

        ViewInteraction appCompatButton = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.button), ViewMatchers.withText("计算"),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.activity_main),
                                        childAtPosition(
                                                ViewMatchers.withClassName(Matchers.is("android.widget.LinearLayout")),
                                                3)),
                                2),
                        ViewMatchers.isDisplayed()));
        appCompatButton.perform(ViewActions.click());

        ViewInteraction textView = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.textView), ViewMatchers.withText("计算结果：15"),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.activity_main),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(
                                                        android.widget.LinearLayout.class),
                                                3)),
                                3),
                        ViewMatchers.isDisplayed()));
        textView.check(ViewAssertions.matches(ViewMatchers.withText("计算结果：15")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
