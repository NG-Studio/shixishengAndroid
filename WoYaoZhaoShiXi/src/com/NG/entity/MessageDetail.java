package com.NG.entity;

/**
 * @author jiyuan
 * class : message 	
 */
public class MessageDetail {
	//Message unique id
	private int uid;
	//Message title
	private String title;
	//Message source like "byr"
	private String source;
	//Message publish time
	private long time;
	//Message original url
	private String source_url;
	
	
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
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getSource_url() {
		return source_url;
	}
	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}
	
}
