package com.NG.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngstudio.zhaoshixi.R;

public class DrawerAdapter extends BaseAdapter{

	private static final String TAG = "adapter";

	private Context mContext;
	private String [] aList;

	public DrawerAdapter(Context context, String [] sList) {
		this.mContext = context;
		Log.d(TAG, "contruct");
		aList = sList;	
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return aList.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (position >= getCount()) {
			return null;
		}
		return aList[position];
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
					R.layout.drawer_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.mTextView = (TextView) convertView
					.findViewById(R.id.drawer_text);
			viewHolder.mImageView = (ImageView) convertView
					.findViewById(R.id.drawer_image);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.mTextView.setText(aList[position]);
		switch(position){
		case 0:
			viewHolder.mImageView.setImageResource(R.drawable.drawer_icon_intern);
			break;
		case 1:
			viewHolder.mImageView.setImageResource(R.drawable.drawer_icon_collect);
			break;
		case 2:
			viewHolder.mImageView.setImageResource(R.drawable.drawer_icon_feedback);
			break;
		case 3:
			viewHolder.mImageView.setImageResource(R.drawable.drawer_icon_aboutus);
			break;
		case 4:
			viewHolder.mImageView.setImageResource(R.drawable.drawer_icon_faq);
			break;
		}
		

		return convertView;
	}

	static class ViewHolder {
		TextView mTextView;
		ImageView mImageView;

	}

}
