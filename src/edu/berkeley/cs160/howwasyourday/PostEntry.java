package edu.berkeley.cs160.howwasyourday;

import android.graphics.Bitmap;

public class PostEntry {
	
	public String discription;
	public int userID;
	public int feeling;
	public String pic;
	public String doodle;
	public String time;
	public String audio;
	public String video;
	
	public PostEntry(int userID, int feeling, String discription, String pic, String doodle, String time, String audio, String video) {
		this.userID = userID;
		this.feeling = feeling;
		this.discription = discription;
		this.pic = pic;
		this.doodle = doodle;
		this.time = time;
		this.audio = audio;
		this.video = video;
	}
	
	
	

}
