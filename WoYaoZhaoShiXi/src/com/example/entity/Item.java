package com.example.entity;

/**
 * @author Administrator
 * Item 类
 * 由于与Message有许多相似之处，所以设计为Message子类
 */
public class Item extends Message{
	
	
	private String text;
	private String author;
	
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	

}
