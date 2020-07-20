package com.example.unittest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.go_about).setOnClickListener(this);
        findViewById(R.id.toast).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_about:
                Intent intent = new Intent(this, AboutActivity.class);
                intent.putExtra("time", 123456);
                startActivity(intent);
                break;
            case R.id.toast:
                Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}