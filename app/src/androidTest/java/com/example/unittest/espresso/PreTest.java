package com.example.unittest.espresso;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.example.unittest.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author shouxianli on 2020/9/29.
 */
@RunWith(AndroidJUnit4.class)
public class PreTest {

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(MainActivity.class);

    /**
     * 查询动画是否开启
     */
    private boolean animationEnabled(Context context) {
        boolean animationEnabled = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                float animationFloat = Settings.Global.getFloat(context.getContentResolver(),
                        Settings.Global.ANIMATOR_DURATION_SCALE);
                Log.e("---anim", String.valueOf(animationFloat));
                if (animationFloat == 0.0f) {
                    animationEnabled = false;
                }
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
        }
        return animationEnabled;
    }

    @Test
    public void testThread() {
        final Activity activity = activityTestRule.getActivity();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, "information", Toast.LENGTH_SHORT).show();
                Log.e("---主线程", Thread.currentThread().getName());
            }
        });

        Log.e("---测试线程", Thread.currentThread().getName());
    }
}
