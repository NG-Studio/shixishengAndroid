package com.NG.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import com.NG.db.ShixiItem;
import com.NG.util.TimeUtils;

import android.util.Log;

public class ShixiItemLoader {
	private static final String TAG = "DetailInfoLoader";

	// private List<ShortComment> mlist = new ArrayList<ShortComment>();
	public ShixiItemLoader() {
		Log.d(TAG, "constractor()  do thing");
	}

	public ShixiItem parserMovieJson(String singleUrl) throws IOException,
			ParserConfigurationException, SAXException {

		ShixiItem item = new ShixiItem();

		URL url = new URL(singleUrl);
		Log.d(TAG, singleUrl);

		// 
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(url.openStream()));
		for (String s = bufferedReader.readLine(); s != null; s = bufferedReader
				.readLine()) {
			stringBuilder.append(s);
		}
		
		Log.d(TAG, stringBuilder.toString());
		
		JSONObject jsonObject = null;
		JSONArray itemsJson = null;

		try {
			
			jsonObject = new JSONObject(stringBuilder.toString());
			
			itemsJson = jsonObject.getJSONArray("data");
			JSONObject ob = itemsJson.getJSONObject(0);
			item = jsonObjectToAwardItem(ob);
			
			Log.d(TAG, item.getTitle());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return item;

	}
	
	public ShixiItem jsonObjectToAwardItem(JSONObject j){
		ShixiItem mItem = new ShixiItem();
		try {
			
			mItem.setTitle(j.getString("item_title"));
			mItem.setText_body(j.getString("item_content"));
			mItem.setSource_url(j.getString("item_url"));
			mItem.setSource(j.getString("item_source"));
			mItem.setTime(j.getString("publish_time"));
			
			//String time;
			//long time_long = Long.parseLong(j.getString("publish_time"));
			//time = TimeUtils.stringToDay(time_long);
			
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mItem;
	}
}