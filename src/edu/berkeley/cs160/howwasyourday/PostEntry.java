package edu.berkeley.cs160.howwasyourday;

import android.graphics.Bitmap;

public class PostEntry {
	
	public String discription;
	public int userID;
	public int feeling;
	public Bitmap pic;
	public Bitmap doodle;
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
