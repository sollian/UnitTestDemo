package com.example.unittest;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.unittest.util.FpsUtil;
import com.example.unittest.util.Person;
import java.lang.reflect.Method;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void onClick(View view) {
        new Dialog(getApplicationContext()).show();
    }
}