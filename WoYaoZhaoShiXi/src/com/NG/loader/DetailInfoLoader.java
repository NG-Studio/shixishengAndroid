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

import com.NG.entity.ItemDetail;

import android.util.Log;

public class DetailInfoLoader {
	private static final String TAG = "DetailInfoLoader";

	// private List<ShortComment> mlist = new ArrayList<ShortComment>();
	public DetailInfoLoader() {
		Log.d(TAG, "constractor()  do thing");
	}

	public ItemDetail parserMovieJson(String singleUrl) throws IOException,
			ParserConfigurationException, SAXException {

		ItemDetail item = new ItemDetail();

		URL url = new URL(singleUrl);
		Log.d(TAG, singleUrl);

		// 获取数据存入StringBuilder里面
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
			//String s = "{status: 1,msg: 'you got 23',data: [{i_title: '主题:[[实习]]清华大学无线中心招聘实习研究生',i_url: 'http://m.byr.cn/article/parttimejob/362622',g_id: '84',i_content: '',i_id: '62'}]}";
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
	
	public ItemDetail jsonObjectToAwardItem(JSONObject j){
		ItemDetail mItem = new ItemDetail();
		try {
			
			mItem.setTitle(j.getString("i_title"));
			mItem.setText(j.getString("i_content"));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mItem;
	}
}
