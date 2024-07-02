package com.baidu.location.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.baidu.location.demo.util.Http;
import com.baidu.location.demo.util.HttpResponse;
import com.baidu.location.demo.vo.EventInfo;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// 主页
public class MenuActivity extends AppCompatActivity {

    // 存储
    private SharedPreferences sharedPreferences;
    private String CHANNEL_ID = "ChannelID";
    private int notificationId = 1;
    private ScheduledExecutorService mScheduledExecutorService;
    private List<EventInfo> infoList=new ArrayList<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // 初始化存储
        sharedPreferences = getSharedPreferences("wms", Context.MODE_PRIVATE);

        // 回退到登录页
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuActivity.this.onBackPressed();
            }
        });


        mScheduledExecutorService = new ScheduledThreadPoolExecutor(6);
        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Set<String> events = new HashSet<>();
                Map<String, ?> allEntries = sharedPreferences.getAll();
                for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                    events.add(entry.getKey());
                }
                EventInfo eventInfo = new EventInfo();
                HttpResponse<EventInfo[]> httpResponse = Http.Request2("list", eventInfo, EventInfo[].class);
                if (httpResponse != null) {
                    for (EventInfo item : httpResponse.Data) {
                        EventInfo info = new EventInfo();
                        info.id = item.id;
                        info.eventName = item.eventName;
                        info.eventTypeName = item.eventTypeName;
                        info.occurrenceAreaName = item.occurrenceAreaName;
                        info.timeOcc = item.timeOcc;

                        if (!events.contains("eventId-" + info.id)) {
                            try {
                                Calendar calendar2 = Calendar.getInstance();
                                if (info.timeOcc.equals(sdf.format(calendar2.getTime()))) {
                                        createNotificationChannel();
                                        Intent intent = new Intent(MenuActivity.this, ListViewActivity.class);
                                        PendingIntent pendingIntent = PendingIntent.getActivity(MenuActivity.this, 0, intent, 0);
                                        NotificationCompat.Builder builder = new NotificationCompat.Builder(MenuActivity.this,CHANNEL_ID)
                                                .setContentTitle(info.eventName)
                                                .setContentText(info.occurrenceAreaName)
                                                .setWhen(System.currentTimeMillis())
                                                .setSmallIcon(R.mipmap.ic_launcher)
                                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                                                .setContentIntent(pendingIntent)
                                                .setVibrate(new long[] {0,1000,1000,1000})
                                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                                .setAutoCancel(true);

                                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MenuActivity.this);

                                        notificationManager.notify(notificationId,builder.build());
                                        sharedPreferences
                                                .edit()
                                                .putString("eventId-" + info.id, info.id)
                                                .commit();
                                    }

                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
        }, 0, 20, TimeUnit.SECONDS);

        findViewById(R.id.btn_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_news).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, NewsAndEventsActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ListViewActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sharedPreferences.getString("identity", "").equals("")) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("")
                .setMessage("Logout")
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MenuActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}