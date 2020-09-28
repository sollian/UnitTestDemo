package com.example.unittest;

import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

/**
 * @author shouxianli on 2020/9/27.
 */
public class MyFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_my);

        if (true) {
            Fragment fragment = new MyFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitAllowingStateLoss();
        } else {
            androidx.fragment.app.Fragment fragment = new MyFragmentX();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitAllowingStateLoss();
        }
    }
}
