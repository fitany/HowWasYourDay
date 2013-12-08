package edu.berkeley.cs160.howwasyourday;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Timeline extends Activity {
	
	MenuItem doodle;
	MenuItem audio;
	MenuItem video;
	MenuItem photo;
	User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_timeline);
         
        //ActionBar actionBar = getActionBar();
        //actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        
        currentUser = LoginPage.getCurUser();

        //View view = View.inflate(getApplicationContext(), R.layout.action_bar_timeline, null);
        //actionBar.setCustomView (view);
		
        
		if(getIntent().getExtras() != null){
			Bundle b = getIntent().getExtras();
			Boolean newPic = b.getBoolean("NEWPIC");
			if(newPic){
				ContextWrapper cw = new ContextWrapper(getApplicationContext());
		        File directory = cw.getDir("letters", Context.MODE_PRIVATE);
			    try {
			        File f=new File(directory, "mypic.bmp");
			        Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(f));
			        ImageButton iv = (ImageButton) findViewById(R.id.imageButton1);
			        iv.setImageBitmap(bm);
			        iv.setBackgroundColor(Color.WHITE);
			    } 
			    catch (FileNotFoundException e) 
			    {
			        e.printStackTrace();
			    }
			}
		}
		
		//get ArrayList of posts from database, hardcoded for now
		/*
		ArrayList<PostEntry> posts = new ArrayList<PostEntry>();
		posts.add(new PostEntry(1, 1, "doodle", new Bitmap(), null));
		posts.add(new PostEntry(1, 1, "doodle", new Bitmap(), null));
		posts.add(new PostEntry(1, 1, "doodle", new Bitmap(), null));
		posts.add(new PostEntry(1, 1, "doodle", new Bitmap(), null));
		//end get ArrayList of posts from database
		
		//Call addNewPost for every post in the ArrayList
		*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.timeline, menu);
    	doodle = menu.findItem(R.id.draw);
    	photo = menu.findItem(R.id.photo);
    	audio = menu.findItem(R.id.audio);
    	video = menu.findItem(R.id.video);
    	return super.onCreateOptionsMenu(menu);
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	      case R.id.draw:
	    	  drawDoodle();
	    	  return true;
	      case R.id.audio:
	    	  recordAudio();
	    	  return true;
	      case R.id.video:
	    	  recordVideo();
	    	  return true;
	      case R.id.photo:
	    	  sharePhoto();
	    	  return true;
	      default:
	            return super.onOptionsItemSelected(item);
	      }
	}
    
    private void drawDoodle() {
    	Intent i = new Intent(this, DrawDoodle.class);
		startActivity(i);
    }
    
    private void sharePhoto() {
    	Intent i = new Intent(this, SharePhoto.class);
		startActivity(i);
    }
    
    private void recordAudio() {
    	Intent i = new Intent(this, RecordAudio.class);
		startActivity(i);
    }
    
    private void recordVideo() {
    	Intent i = new Intent(this, RecordVideo.class);
		startActivity(i);
    }
    
    private void addNewPost(Post post){
    	
    }
}
