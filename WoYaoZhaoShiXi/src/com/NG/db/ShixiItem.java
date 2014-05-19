package com.NG.db;

public class ShixiItem {

	private int item_id;
	private String title;
	private String source;
	private String time;
	private String source_url;
	private String text_body;
	private boolean is_clicked;
	private boolean is_collected;
	
	public ShixiItem() {
		
	}
	
	public ShixiItem(int item_id, String title, String source, String time,
			String source_url, String text_body ,boolean is_clicked,boolean is_collected) {
		this.item_id = item_id;
		this.title = title;
		this.source = source;
		this.time = time;
		this.source_url = source_url;
		this.text_body = text_body;
		this.is_clicked = is_clicked;
		this.is_collected = is_collected;
	}

	/**
	 * @return the item_id
	 */
	public int getItem_id() {
		return item_id;
	}

	/**
	 * @param item_id the item_id to set
	 */
	public void setItem_id(int item_id) {
		this.item_id = item_id;
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
	 * @return the text_body
	 */
	public String getText_body() {
		return text_body;
	}

	/**
	 * @param text_body the text_body to set
	 */
	public void setText_body(String text_body) {
		this.text_body = text_body;
	}

	public boolean isIs_clicked() {
		return is_clicked;
	}

	public void setIs_clicked(boolean is_clicked) {
		this.is_clicked = is_clicked;
	}

	public boolean isIs_collected() {
		return is_collected;
	}

	public void setIs_collected(boolean is_collected) {
		this.is_collected = is_collected;
	}
	
	
}
