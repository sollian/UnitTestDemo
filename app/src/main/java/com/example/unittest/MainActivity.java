package com.example.unittest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements OnClickListener {

    private EditText vNum1;
    private EditText vNum2;
    private TextView vSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.go_about).setOnClickListener(this);
        findViewById(R.id.toast).setOnClickListener(this);
        findViewById(R.id.go_web).setOnClickListener(this);
        findViewById(R.id.go_list).setOnClickListener(this);

        vNum1 = findViewById(R.id.num1);
        vNum2 = findViewById(R.id.num2);
        vSum = findViewById(R.id.sum);
        findViewById(R.id.calculate).setOnClickListener(this);

        TextView vListResult = findViewById(R.id.list_result);
        ListView vList = findViewById(R.id.list);
        List<Map<String, String>> data = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Map<String, String> map = new ArrayMap<>();
            map.put("id", String.valueOf(i));
            data.add(map);
        }
        vList.setAdapter(new SimpleAdapter(this,
                data,
                android.R.layout.simple_list_item_1,
                new String[]{"id"},
                new int[]{android.R.id.text1}
        ));
        vList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vListResult.setText(String.valueOf(position));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_about: {
                Intent intent = new Intent(this, AboutActivity.class);
                intent.putExtra("time", 123456);
                startActivity(intent);
            }
            break;
            case R.id.toast:
                Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
                break;
            case R.id.go_web: {
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.EXTRA_URL, "https://m.baidu.com");
                startActivity(intent);
            }
            break;
            case R.id.go_list:
                Intent intent = new Intent(this, RecycleviewActivity.class);
                startActivityForResult(intent, 0x1000);
                break;
            case R.id.calculate:
                String str = vNum1.getText().toString().trim();
                if (TextUtils.isEmpty(str)) {
                    Toast.makeText(this, "请输入数字1", Toast.LENGTH_SHORT).show();
                    return;
                }
                String str2 = vNum2.getText().toString().trim();
                if (TextUtils.isEmpty(str2)) {
                    Toast.makeText(this, "请输入数字2", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    int num1 = Integer.parseInt(str);
                    int num2 = Integer.parseInt(str2);
                    vSum.setText("计算结果：" + (num1 + num2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            vSum.setText(data.getStringExtra("data"));
        }
    }
}