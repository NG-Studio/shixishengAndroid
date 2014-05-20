package com.NG.activity;

import java.util.Date;

import android.app.Fragment;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
	private Button cancelBtn;
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
		
		this.getActivity().setTitle("吐槽我们");
		mac = getLocalMacAddress(mContext);;
		
		editName = (EditText)rootView.findViewById(R.id.editName);
		editContent = (EditText)rootView.findViewById(R.id.editContent);
		
		sendBtn=(Button)rootView.findViewById(R.id.btnSend);
		sendBtn.setOnClickListener(new View.OnClickListener() { 
			public void onClick(View v) { 
				SendTask sTask = new SendTask();    
				sTask.execute();
			} 
		}); 
		
		cancelBtn=(Button)rootView.findViewById(R.id.btnCancel);
		cancelBtn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				editName.setText("");
				editContent.setText("");
				mac = getLocalMacAddress(mContext);
				//Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();  
			}
			
		});
		
		
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
			Toast.makeText(mContext, "Begin Send!", Toast.LENGTH_SHORT).show();  
			super.onPreExecute();    
		}    

		@Override    
		protected String doInBackground(Integer... params) {    
			//第二个执行方法,onPreExecute()执行完后执行    
			// TODO Auto-generated method stub  
			Mail m = new Mail("ngstudiochina@gmail.com", "ngstudio2014"); 

			String[] toArr = {"jiyuan0371@gmail.com","ngstudiochina@googlegroups.com"}; 
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
			body += "-------------------------当前邮件来自哇实习客户端";

			m.setBody(body);

			try {
				//If you want add attachment use function addAttachment.
				//m.addAttachment("/sdcard/filelocation"); 

				if(m.send()) { 
					System.out.println("Email was sent successfully."); 
				} else { 
					System.out.println("Email was not sent.");
				} 
			} catch(Exception e) { 
				Toast.makeText(mContext, "There was a problem sending the email.", Toast.LENGTH_LONG).show(); 
				Log.e("MailApp", "Could not send email	", e); 
			}

			return "";    
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
			super.onPostExecute(r);    
		}    
		
	    
        

	}    
	
	
	
}
