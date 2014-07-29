package com.NG.activity;

import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import com.ngstudio.zhaoshixi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FaqFragment extends Fragment {
	private TextView tv;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_faq, container,
				false);

		tv = (TextView) rootView.findViewById(R.id.tv_faq);
		String html = getFromAssets("FAQ.html");
		tv.setText(Html.fromHtml(html));

		return rootView;
	}

	// 从assets 文件夹中获取文件并读取数据
	public String getFromAssets(String fileName) {
		String result = "";
		try {
			InputStream in = getResources().getAssets().open(fileName);
			// 获取文件的字节数
			int lenght = in.available();
			// 创建byte数组
			byte[] buffer = new byte[lenght];
			// 将文件中的数据读到byte数组中
			in.read(buffer);
			result = EncodingUtils.getString(buffer, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
