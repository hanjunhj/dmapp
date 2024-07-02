package com.baidu.location.demo.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.baidu.location.demo.R;
import com.baidu.location.demo.vo.EventInfo;

import java.util.List;

public class MyAdapter extends ArrayAdapter<EventInfo> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<EventInfo> listInfo;
    public MyAdapter(@NonNull Context context, int resource, @NonNull List<EventInfo> list) {
        super(context, resource, list);
        this.mContext=context;
        mLayoutInflater =LayoutInflater.from(context);
        listInfo=list;
    }

    public void add(EventInfo info){
        listInfo.add(info);
        notifyDataSetChanged();
    }

    static class ViewHolder{
        public ImageView imageView;
        public TextView tvTitle,tvType,tvContext,tvLon,tvLat,tvOccTime,tvDesc,tvLocation;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ViewHolder
        EventInfo info = (EventInfo) getItem(position);//
        ViewHolder holder = null;
        if (convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.layout_list_item,null);
            holder = new ViewHolder();
            holder.tvTitle= (TextView) convertView.findViewById(R.id.tv_listTitle_Id);
            holder.tvType = (TextView) convertView.findViewById(R.id.tv_listType_Id);
            holder.tvContext= (TextView) convertView.findViewById(R.id.tv_listContext_Id);
            holder.tvOccTime= (TextView) convertView.findViewById(R.id.tv_listTime_Id);
            holder.tvLat = (TextView) convertView.findViewById(R.id.tv_listlat_Id);
            holder.tvLon= (TextView) convertView.findViewById(R.id.tv_listlon_Id);
            holder.tvDesc= (TextView) convertView.findViewById(R.id.tv_listinfo_Id);
            holder.tvLocation = (TextView) convertView.findViewById(R.id.tv_listlocation_Id);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText("Event:" + info.getEventName());
        holder.tvType.setText("Type:" + info.getEventTypeName());
        holder.tvContext.setText("Area:"+ info.getOccurrenceAreaName());
        holder.tvOccTime.setText("Time:" + info.getTimeOcc() + " " + info.getDateTimeOcc());
        holder.tvLat.setText("Lat:" + info.getLatValue());
        holder.tvLon.setText("Lon:" + info.getLonValue());
        holder.tvDesc.setText("Description:" + info.getEventDesc());
        holder.tvLocation.setText("Location:" + info.getLocation());
        return convertView;
    }
}