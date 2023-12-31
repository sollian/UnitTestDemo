package com.example.unittest.espresso;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.fragment.app.testing.FragmentScenario.FragmentAction;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.unittest.MyFragmentX;
import com.example.unittest.R;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 测试类
 *
 * @author shouxianli on 2020/9/27.
 */
@RunWith(AndroidJUnit4.class)
public class FragmentTest {

    @Test
    public void testFragmentX() {
        Bundle bundle = new Bundle();
        bundle.putString("name", "test name");
        FragmentScenario<MyFragmentX> fragmentScenario = FragmentScenario.launchInContainer(
                MyFragmentX.class, bundle);

        fragmentScenario.onFragment(new FragmentAction<MyFragmentX>() {
            @Override
            public void perform(@NonNull MyFragmentX fragment) {
                TextView vName = fragment.getView().findViewById(R.id.name);

                String text = vName.getText().toString();
                System.out.println(text);
                Assert.assertEquals("test name", text);
            }
        });
    }

    @Test
    public void testFragmentX2() {
        Bundle bundle = new Bundle();
        bundle.putString("name", "test name");
        FragmentScenario.launchInContainer(MyFragmentX.class, bundle);

        Espresso.onView(ViewMatchers.withId(R.id.name))
                .check(ViewAssertions.matches(ViewMatchers.withText("test name")));
    }
}
