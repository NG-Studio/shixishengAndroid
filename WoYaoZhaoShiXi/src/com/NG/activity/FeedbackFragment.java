package com.NG.activity;


import java.util.Date;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.NG.util.Mail;
import com.NG.util.TimeUtils;
import com.ngstudio.zhaoshixi.R;

public class FeedbackFragment extends Fragment{
	
	private Context mContext;
	
	private Button sendBtn;
//	private Button cancelBtn;
	private EditText editName;
	private EditText editContent;
	
	private long time_now;
	
	public static String mac;
	
	public FeedbackFragment(){
		
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_feedback, container,
				false);
		mContext = this.getActivity().getApplicationContext();
		
		this.getActivity().setTitle("吐槽一下");
		mac = getLocalMacAddress(mContext);;
		
		editName = (EditText)rootView.findViewById(R.id.editName);		
		editContent = (EditText)rootView.findViewById(R.id.editContent);
		//获得焦点时，hint变成空白
		editName.setOnFocusChangeListener(new OnFocusChangeListener() {
		    public void onFocusChange(View v, boolean hasFocus) {
		        EditText _v=(EditText)v;
		        if (!hasFocus) {// 失去焦点
		            _v.setHint(_v.getTag().toString());
		        } else {
		            String hint=_v.getHint().toString();
		            _v.setTag(hint);
		            _v.setHint("");
		        }
		    }
		});
		editContent.setOnFocusChangeListener(new OnFocusChangeListener() {
		    public void onFocusChange(View v, boolean hasFocus) {
		        EditText _v=(EditText)v;
		        if (!hasFocus) {// 失去焦点
		            _v.setHint(_v.getTag().toString());
		        } else {
		            String hint=_v.getHint().toString();
		            _v.setTag(hint);
		            _v.setHint("");
		        }
		    }
		});
		
		sendBtn=(Button)rootView.findViewById(R.id.btnSend);
		sendBtn.setOnClickListener(new View.OnClickListener() { 
			public void onClick(View v) { 
				SendTask sTask = new SendTask();    
				sTask.execute();
			} 
		}); 
//		
//		cancelBtn=(Button)rootView.findViewById(R.id.btnCancel);
//		cancelBtn.setOnClickListener(new View.OnClickListener(){
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				editName.setText("");
//				editContent.setText("");
//				//mac = getLocalMacAddress(mContext);
//				//Toast.makeText(mContext, mac, Toast.LENGTH_SHORT).show(); 
//				/*
//				// 用户点击取消后返回首页
//				Toast.makeText(mContext, "欢迎您有空再来吐槽^-^", Toast.LENGTH_LONG).show(); 
//				
//				// 返回首页
//				Intent intent = new Intent();  
//	            // 设置Intent的源地址和目标地址  
//	            intent.setClass(getActivity(), Main.class);  
//	            // 调用startActivity方法发送意图给系统  
//	            startActivity(intent);
//	            */
//			}
//			
//		});
		
		
		return rootView;
	}
	
	/** 
     * 获取mac地址 
     * @param context 
     * @return 
     */  
    public String getLocalMacAddress(Context context) {  
        WifiManager wifi = (WifiManager) context.getSystemService(  
                Context.WIFI_SERVICE);  
        WifiInfo info = wifi.getConnectionInfo();  
        return info.getMacAddress();  
    }

    
	
	class SendTask extends AsyncTask<Integer, Integer, String>{    
		//后面尖括号内分别是参数（例子里是线程休息时间），进度(publishProgress用到)，返回值 类型    

		@Override    
		protected void onPreExecute() {    
			//第一个执行方法
			if(editContent.length() > 0) {
				// 置sendBtn为不可点击状态
				sendBtn.setClickable(false);
				sendBtn.setEnabled(false);
				
				Toast.makeText(mContext, "正在把您的槽点运往远方...请不要着急关闭应用哦^_^", Toast.LENGTH_SHORT).show(); 
				/*
				// 返回首页
				Intent intent = new Intent();  
	            // 设置Intent的源地址和目标地址  
	            intent.setClass(getActivity(), Main.class);  
	            // 调用startActivity方法发送意图给系统  
	            startActivity(intent);
	            */
			}
			else {
				
			}
			super.onPreExecute();    
		}    

		@Override    
		protected String doInBackground(Integer... params) {    
			//第二个执行方法,onPreExecute()执行完后执行    
			// TODO Auto-generated method stub  
			String retStr = "";
			
			if (editContent.length() == 0)
			{
				retStr = "too_little";
				return retStr;
			}
			
			Mail m = new Mail("shixiwa.feedback@gmail.com", "shixiwafeedback"); 
			String[] toArr = {"jiyuan0371@gmail.com","washixi@googlegroups.com"}; 
			
			m.setTo(toArr); 
			m.setFrom("wooo@wooo.com"); 
			
			String subject = "【用户反馈邮件】";
			m.setSubject(subject);

			Date d = new Date();
			time_now = d.getTime() / 1000;
			String body = "From " + editName.getText().toString() + ":\n\n";
			body += editContent.getText().toString() + "\n\n";
			body += "-------------------------";
			body += TimeUtils.longToMinute(time_now) + "\n\n";
			body += "-------------------------该用户mac地址为  " + mac + "\n";
			try {
				body += "-------------------------当前邮件来自哇实习客户端 "+getVersionName();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				body += "-------------------------当前邮件来自哇实习客户端";
				e1.printStackTrace();
			}

			m.setBody(body);

			try {
				//If you want add attachment use function addAttachment.
				//m.addAttachment("/sdcard/filelocation"); 

				if(m.send()) { 
					retStr = "success";
					System.out.println("Email was sent successfully."); 
				} else { 
					retStr = "fail";
					System.out.println("Email was not sent.");
				} 
			} catch(Exception e) { 
				retStr = "fail";
				//Toast.makeText(mContext, "There was a problem sending the email.", Toast.LENGTH_LONG).show(); 
				Log.e("MailApp", "Could not send email	", e); 
			} finally {
				// Don't exit the app
			}

			return retStr;    
		}    

		@Override    
		protected void onProgressUpdate(Integer... progress) {    
			//这个函数在doInBackground调用publishProgress时触发，虽然调用时只有一个参数    
			//但是这里取到的是一个数组,所以要用progesss[0]来取值    
			//第n个参数就用progress[n]来取值        
			super.onProgressUpdate(progress);    
		}    

		@Override    
		protected void onPostExecute(String r) {    
			//doInBackground返回时触发，换句话说，就是doInBackground执行完后触发    
			//这里的result就是上面doInBackground执行后的返回值，所以这里是"执行完毕"    
			//setTitle(result);  
			if(r == "success") {
				Toast.makeText(mContext, "槽点已成功抵达，感谢您的支持！", Toast.LENGTH_LONG).show(); 
				
			}
			else if(r == "fail") {
				Toast.makeText(mContext, "抱歉，槽点未能发送，请稍后重试！", Toast.LENGTH_LONG).show(); 
			}
			else if(r == "too_little") {
				Toast.makeText(mContext, "亲，请随便说点什么吧^_^", Toast.LENGTH_LONG).show(); 
			}
			else {
				
			}
			
			// 置sendBtn为可点击状态
			sendBtn.setClickable(true);
			sendBtn.setEnabled(true);
			
			super.onPostExecute(r);    
		}   
		
		/*
		 * 获取当前程序的版本号
		 */
		private String getVersionName() throws Exception {
			// 获取packagemanager的实例
			PackageManager packageManager = mContext.getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(),
					0);
			return packInfo.versionName;
		}
	}    
}
