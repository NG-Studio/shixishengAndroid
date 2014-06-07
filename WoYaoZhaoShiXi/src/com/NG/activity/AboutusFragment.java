package com.NG.activity;

import java.io.IOException;

import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.ngstudio.zhaoshixi.R;

public class AboutusFragment extends Fragment {

	private Context mContext;
	private Button checkVersionBth;
	
	private String httpUrl = "http://211.155.86.159/online/info/check_version";

	public AboutusFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = this.getActivity().getApplicationContext();
		

		View rootView = inflater.inflate(R.layout.fragment_aboutus, container,
				false);
		checkVersionBth = (Button) rootView
				.findViewById(R.id.btn_check_version);
		checkVersionBth.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				String resultText = "";

				HttpGet httpRequest = new HttpGet(httpUrl);
				// 取得HttpClient对象
				HttpClient httpclient = new DefaultHttpClient();
				// 请求HttpClient，取得HttpResponse
				HttpResponse httpResponse;
				try {
					httpResponse = httpclient.execute(httpRequest);

					// 请求成功
					if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						// 取得返回的字符串
						String strResult = EntityUtils.toString(httpResponse
								.getEntity());
						resultText = strResult;
					} else {
						resultText = "请求错误!";
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				try {
					
					Toast.makeText(mContext, "当前版本为"+getVersionName(), Toast.LENGTH_SHORT).show();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

		return rootView;
	}

	private String getVersionName() throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = mContext.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(
				mContext.getPackageName(), 0);
		String version = packInfo.versionName;
		return version;
	}

}
