package com.example.unittest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;

/**
 * 测试类
 *
 * @author shouxianli on 2020/7/17.
 */
public class MyService extends Service {

    private final String tag = MyService.class.getSimpleName();

    private String msg;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(tag, "-------onCreate-------");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        msg = intent.getStringExtra("test");
        Log.i(tag, "-------test-------" + msg);
        Log.i(tag, "-------flags-------" + flags);
        Log.i(tag, "-------startId-------" + startId);
        Log.i(tag, "-------onStartCommand-------");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(tag, "-------onDestroy-------");
        super.onDestroy();
    }

    public String getMsg() {
        return msg;
    }
}
