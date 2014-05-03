package com.NG.activity;

import com.ngstudio.zhaoshixi.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class webActivity extends Activity {

	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);

		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);// ����ʹ�ù�ִ��JS�ű�
		webView.getSettings().setBuiltInZoomControls(true);// ����ʹ֧������
		// webView.getSettings().setDefaultFontSize(5);
		
		Bundle bundle = getIntent().getExtras();
		String path = bundle.getString("item_url");
		webView.loadUrl(path);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);// ʹ�õ�ǰWebView������ת
				return true;// true��ʾ���¼��ڴ˴������?����Ҫ�ٹ㲥
			}

			@Override
			// ת�����ʱ�Ĵ���
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				Toast.makeText(webActivity.this, "Oh no! " + description,
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	// Ĭ�ϵ���˼���˳�Activity�������������ʹ������WebView�ڷ���
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
