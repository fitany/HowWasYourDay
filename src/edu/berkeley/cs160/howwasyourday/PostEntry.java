package edu.berkeley.cs160.howwasyourday;

import android.graphics.Bitmap;

public class PostEntry {
	
	private String discription;
	private int userID;
	private int feeling;
	private Bitmap pic;
	private Bitmap doodle;
	public String time;
	
	public PostEntry(int userID, int feeling, String discription, Bitmap pic, Bitmap doodle, String time) {
		this.userID = userID;
		this.feeling = feeling;
		this.discription = discription;
		this.pic = pic;
		this.doodle = doodle;
		this.time = time;
	}
	
	
	

}