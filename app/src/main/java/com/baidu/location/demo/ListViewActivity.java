package com.baidu.location.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.baidu.location.demo.util.Http;
import com.baidu.location.demo.util.HttpResponse;
import com.baidu.location.demo.util.KeyValuePair;
import com.baidu.location.demo.util.MyAdapter;
import com.baidu.location.demo.vo.EventInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewActivity extends AppCompatActivity {

    private ListView mLV1;
    private ImageButton btn_back;
    private List<EventInfo> infoList=new ArrayList<>();
    MyAdapter adapter;
    private List<Map<String, Object>> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        mLV1=(ListView) findViewById(R.id.lv_1_Id);
        this.btn_back = findViewById(R.id.btn_back);
        this.btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListViewActivity.this.finish();
            }
        });

        loadData();
        adapter=new MyAdapter(ListViewActivity.this,R.layout.layout_list_item,infoList);
        mLV1.setAdapter(adapter);
        mLV1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventInfo eventInfo = (EventInfo)mLV1.getItemAtPosition(position);
                Intent _intent = new Intent(ListViewActivity.this,MapActivity.class);
                _intent.putExtra("lon",eventInfo.lonValue);
                _intent.putExtra("lat",eventInfo.latValue);
                startActivity(_intent);
            }
        });
    }
    private void loadData() {
        EventInfo eventInfo = new EventInfo();
        HttpResponse<EventInfo[]> httpResponse = Http.Request2("list", eventInfo, EventInfo[].class);
        if (httpResponse != null) {
            this.items.clear();
            for (EventInfo item : httpResponse.Data) {
                EventInfo info = new EventInfo();
                info.latValue = item.latValue;
                info.lonValue = item.lonValue;
                info.eventName = item.eventName;
                info.eventTypeName = item.eventTypeName;
                info.occurrenceArea = item.occurrenceArea;
                info.occurrenceAreaName = item.occurrenceAreaName;
                info.timeOcc = item.timeOcc;
                info.location = item.location;
                info.dateTimeOcc = item.dateTimeOcc;
                info.eventDesc = item.eventDesc;
                infoList.add(info);
            }
        }
    }
}