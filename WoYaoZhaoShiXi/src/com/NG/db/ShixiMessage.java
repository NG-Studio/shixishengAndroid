package com.NG.db;

public class ShixiMessage {
	private int message_id;
	private String title;
	private String source;
	private String time;
	private String source_url;
	private String is_clicked;
	
	public ShixiMessage() {
		
	}
	
	public ShixiMessage(int message_id, String title, String source, String time, String source_url, String is_clicked) {
		this.message_id = message_id;
		this.title = title;
		this.source = source;
		this.time = time;
		this.source_url = source_url;
		this.is_clicked = is_clicked;
	}

	/**
	 * @return the message_id
	 */
	public int getMessage_id() {
		return message_id;
	}

	/**
	 * @param message_id the message_id to set
	 */
	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the source_url
	 */
	public String getSource_url() {
		return source_url;
	}

	/**
	 * @param source_url the source_url to set
	 */
	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}

	/**
	 * @return the is_clicked
	 */
	public String getIs_clicked() {
		return is_clicked;
	}

	/**
	 * @param is_clicked the is_clicked to set
	 */
	public void setIs_clicked(String is_clicked) {
		this.is_clicked = is_clicked;
	}

	
}
