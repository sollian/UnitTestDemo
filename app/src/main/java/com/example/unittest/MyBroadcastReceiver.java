package com.example.unittest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author shouxianli on 2020/7/17.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {

    private final String tag = MyBroadcastReceiver.class.getSimpleName();

    private String msg;

    @Override
    public void onReceive(Context context, Intent intent) {
        //获取广播的数据.
        msg = intent.getStringExtra("test");
        Log.i(tag, "静态广播接收消息....." + msg);
    }

    public String getMsg() {
        return msg;
    }
}
