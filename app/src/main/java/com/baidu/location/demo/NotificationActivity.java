package com.baidu.location.demo;

import android.app.NotificationManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);//设置方法取消显示通知内容
        manager.cancel(1);
    }
}