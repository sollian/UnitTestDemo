package com.example.unittest;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * activity基类
 *
 * @author shouxianli on 2020/7/17.
 */
public class BaseActivity extends AppCompatActivity {

    private final String tag = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.w(tag, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        Log.w(tag, "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.w(tag, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.w(tag, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.w(tag, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.w(tag, "onDestroy");
        super.onDestroy();
    }
}
