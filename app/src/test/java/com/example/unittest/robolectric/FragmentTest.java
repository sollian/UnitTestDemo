package com.example.unittest.robolectric;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.fragment.app.testing.FragmentScenario.FragmentAction;
import com.example.unittest.MyFragment;
import com.example.unittest.MyFragmentX;
import com.example.unittest.R;
import org.junit.Assert;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.FragmentController;

/**
 * @author shouxianli on 2020/9/27.
 */
public class FragmentTest extends BaseRobolectricTest {

    @Test
    public void testFragment() {
        FragmentController controller = Robolectric.buildFragment(MyFragment.class);
        Bundle bundle = new Bundle();
        bundle.putString("name", "test name");
        MyFragment fragment = (MyFragment) controller.get();
        fragment.setArguments(bundle);

        controller.create();
        controller.start();
        controller.resume();

        TextView vName = fragment.getView().findViewById(R.id.name);

        Assert.assertEquals("test name", vName.getText());
    }

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
}
