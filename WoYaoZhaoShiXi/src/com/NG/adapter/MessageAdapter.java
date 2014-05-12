package com.NG.adapter;

import java.util.ArrayList;
import java.util.List;

import com.NG.entity.MessageDetail;
import com.NG.util.TimeUtils;
import com.ngstudio.zhaoshixi.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter {

	private static final String TAG = "adapter";
	private boolean mBusy = false;

	public void setFlagBusy(boolean busy) {
		this.mBusy = busy;
	}

	private Context mContext;
	private List<MessageDetail> aList;

	public MessageAdapter(Context context, List<MessageDetail> seList) {
		this.mContext = context;
		Log.d(TAG, "contruct");
		aList = seList;
		for(int i=0;i<seList.size();i++){
			Log.d(TAG, seList.get(0).getTitle());
		}
		
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return aList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (position >= getCount()) {
			return null;
		}
		return aList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.message_main_item, null);
			viewHolder = new ViewHolder();
			viewHolder.titleView = (TextView) convertView
					.findViewById(R.id.all_title);
			viewHolder.timeView = (TextView) convertView
					.findViewById(R.id.all_time);
			viewHolder.sourceView = (TextView) convertView
					.findViewById(R.id.all_source);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final MessageDetail m = aList.get(position);
		
		Log.d(TAG, m.getTitle()+m.getTime()+m.getSource());

		viewHolder.titleView.setText(m.getTitle());
		viewHolder.timeView.setText(TimeUtils.stringToMinuteWithoutYear(m.getTime()));
		viewHolder.sourceView.setText(m.getSource());

		return convertView;
	}

	static class ViewHolder {
		TextView titleView;
		TextView timeView;
		TextView sourceView;
		ImageView mImageView;

	}

}
