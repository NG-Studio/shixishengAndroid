/*
 * Offical Website:http://www.ShareSDK.cn
 * Support QQ: 4006852216
 * Offical Wechat Account:ShareSDK   (We will inform you our updated news at the first time by Wechat, if we release a new version. If you get any problem, you can also contact us with Wechat, we will reply you within 24 hours.)
 *
 * Copyright (c) 2013 ShareSDK.cn. All rights reserved.
 */

package com.NG.util;

import android.util.Log;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.system.email.Email;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.moments.WechatMoments;


/**
 * OnekeyShare allows you to customize share content according to
 * the target platform by implements ShareContentCustomizeCallback
 * and override its {@link #onShare(Platform, ShareParams)} method
 *
 * This demo defines a callback to shorten share content of Twitter.
 */
public class ShareContentCustomizeInterface implements ShareContentCustomizeCallback {

	public void onShare(Platform platform, ShareParams paramsToShare) {
		// customize the contents according to the platform's name
		if ((SinaWeibo.NAME).equals(platform.getName())) {
			Log.i("shareSDK", "Sharing SinaWeibo...");
			
			String oldText = paramsToShare.getText();
			String oldUrl = paramsToShare.getUrl();
			
			Log.i("shareSDK", "oldText:" + oldText);
			Log.i("shareSDK", "oldUrl:" + oldUrl);
			
			paramsToShare.setText(oldText + "-" + oldUrl);
		}
		
		else if ((Email.NAME).equals(platform.getName())) {
			Log.i("shareSDK", "Sharing Email...");
			
			String oldText = paramsToShare.getText();
			String oldUrl = paramsToShare.getUrl();
			
			Log.i("shareSDK", "oldText:" + oldText);
			Log.i("shareSDK", "oldUrl:" + oldUrl);
			
			paramsToShare.setText(oldText + "-" + oldUrl);
		}
		
		else if ((ShortMessage.NAME).equals(platform.getName())) {
			Log.i("shareSDK", "Sharing ShortMessage...");
			
			String oldText = paramsToShare.getText();
			String oldUrl = paramsToShare.getUrl();
			
			Log.i("shareSDK", "oldText:" + oldText);
			Log.i("shareSDK", "oldUrl:" + oldUrl);
			
			paramsToShare.setText(oldText + "-" + oldUrl);
		}
		
		else if ((TencentWeibo.NAME).equals(platform.getName())) {
			Log.i("shareSDK", "Sharing TecentWeibo...");
			
			String oldText = paramsToShare.getText();
			String oldUrl = paramsToShare.getUrl();
			
			Log.i("shareSDK", "oldText:" + oldText);
			Log.i("shareSDK", "oldUrl:" + oldUrl);
			
			paramsToShare.setText(oldText + "-" + oldUrl);
		}
		
		else if ((QZone.NAME).equals(platform.getName())) {
			Log.i("shareSDK", "Sharing QZone...");
			
			String oldText = paramsToShare.getText();
			String oldUrl = paramsToShare.getUrl();
			
			Log.i("shareSDK", "oldText:" + oldText);
			Log.i("shareSDK", "oldUrl:" + oldUrl);
			
			paramsToShare.setText(oldText + "-" + oldUrl);
		}
		
		else if ((WechatMoments.NAME).equals(platform.getName())) {
			Log.i("shareSDK", "Sharing WechatMoments...");
			
			String oldText = paramsToShare.getText();
			String oldUrl = paramsToShare.getUrl();
			
			Log.i("shareSDK", "oldText:" + oldText);
			Log.i("shareSDK", "oldUrl:" + oldUrl);
			
			//paramsToShare.setText(oldText + "-" + oldUrl);
			paramsToShare.setTitle(oldText);
		}
		
		else if ((WechatFavorite.NAME).equals(platform.getName())) {
			Log.i("shareSDK", "Sharing WechatFavorite...");
			
			String oldText = paramsToShare.getText();
			String oldUrl = paramsToShare.getUrl();
			
			Log.i("shareSDK", "oldText:" + oldText);
			Log.i("shareSDK", "oldUrl:" + oldUrl);
			
			//paramsToShare.setText(oldText + "-" + oldUrl);
			paramsToShare.setTitle(oldText);
		}
		
		else {
			
		}


	}

}
