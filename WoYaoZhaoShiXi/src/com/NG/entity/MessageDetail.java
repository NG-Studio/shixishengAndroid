package com.NG.entity;

/**
 * @author jiyuan
 * message类		
 */
public class MessageDetail {
	//每个Message唯一的id
	private int uid;
	//Message的标题
	private String title;
	//Message的来源，如北邮人论坛
	private String source;
	//Message的发布时间
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
