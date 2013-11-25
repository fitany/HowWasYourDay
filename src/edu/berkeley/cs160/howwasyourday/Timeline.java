package edu.berkeley.cs160.howwasyourday;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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

        View view = View.inflate(getApplicationContext(), R.layout.activity_timeline,
                null);
        actionBar.setCustomView(view);
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
