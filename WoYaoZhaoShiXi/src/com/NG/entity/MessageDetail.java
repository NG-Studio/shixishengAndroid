package com.NG.entity;

/**
 * @author jiyuan
 * message��		
 */
public class MessageDetail {
	//ÿ��MessageΨһ��id
	private int uid;
	//Message�ı���
	private String title;
	//Message����Դ���籱������̳
	private String source;
	//Message�ķ���ʱ��
	private String time;
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
