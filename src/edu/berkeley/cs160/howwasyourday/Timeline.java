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
import android.widget.ImageButton;
import android.widget.ImageView;

public class Timeline extends Activity {
	
	MenuItem doodle;
	MenuItem audio;
	MenuItem video;
	MenuItem photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
         
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        View view = View.inflate(getApplicationContext(), R.layout.action_bar_timeline,
                null);
        actionBar.setCustomView (view);
		
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
    
}
