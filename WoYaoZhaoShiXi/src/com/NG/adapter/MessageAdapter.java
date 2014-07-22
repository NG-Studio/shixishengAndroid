package com.NG.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.NG.db.ShixiMessage;
import com.NG.util.TimeUtils;
import com.ngstudio.zhaoshixi.R;

public class MessageAdapter extends BaseAdapter {

    private static final String TAG = "adapter";

    private boolean mBusy = false;

    // private Typeface face;

    public void setFlagBusy(boolean busy) {
        this.mBusy = busy;
    }

    private Context mContext;

    private List<ShixiMessage> aList;

    public MessageAdapter(Context context, List<ShixiMessage> seList) {
        this.mContext = context;
        Log.d(TAG, "contruct");
        aList = seList;
        /*
         * for(int i=0;i<seList.size();i++){ Log.d(TAG,
         * seList.get(0).getTitle()); }
         */
        // face = Typeface.createFromAsset(mContext.getAssets(),
        // "fonts/FashionBlack.ttf");

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
                    R.layout.fragment_message_item, null);
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
        final ShixiMessage m = aList.get(position);

        Log.d(TAG, m.getTitle() + m.getTime() + m.getSource());

        viewHolder.titleView.setText(m.getTitle());
        viewHolder.timeView.setText(TimeUtils.stringToMinuteWithoutYear(m
                .getTime()));
        viewHolder.sourceView.setText(m.getSource());
        // viewHolder.titleView.setTypeface(face);
        // viewHolder.sourceView.setTypeface(face);
        // viewHolder.timeView.setTypeface(face);

        return convertView;
    }

    static class ViewHolder {
        TextView titleView;

        TextView timeView;

        TextView sourceView;

        ImageView mImageView;

    }

}
