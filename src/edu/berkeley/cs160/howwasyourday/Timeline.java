package edu.berkeley.cs160.howwasyourday;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import edu.berkeley.cs160.howwasyourday.database.DatabaseHelper;

public class Timeline extends Activity {
	
	MenuItem doodle;
	MenuItem audio;
	MenuItem video;
	MenuItem photo;
	MenuItem timeline;
	MenuItem new_event;
	MenuItem stats;
	User currentUser;
	Boolean isChild=true;
	DatabaseHelper db;
	SQLiteDatabase database;

    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
         
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        
        currentUser = LoginPage.getCurUser();
        String userType = currentUser.type;
        if(currentUser.type.equals("Children"))
        	isChild = true;
        else
        	isChild = false;
        
        View view = View.inflate(getApplicationContext(), R.layout.action_bar_timeline, null);
        actionBar.setCustomView (view);
		
		//get ArrayList of posts from database, hardcoded for now
        db = new DatabaseHelper(this);
		database = db.getWritableDatabase();
		ArrayList<PostEntry> posts = db.getAllPost(database,currentUser.familyId);
		/*
		Bitmap bm = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.playing);
		ArrayList<PostEntry> posts = new ArrayList<PostEntry>();
		posts.add(new PostEntry(1, 1, "doodle", bm, null, null));
		posts.add(new PostEntry(2, 1, "doodle", bm, null, null));
		posts.add(new PostEntry(1, 1, "doodle", bm, null, null));
		posts.add(new PostEntry(2, 1, "doodle", bm, null, null));
		//end get ArrayList of posts from database
		*/
		
		//Call addNewPost for every post in the ArrayList
		for(int i = 0; i < posts.size(); i++){
			PostEntry p = posts.get(i);
			addNewPost(p,i);
		}
		
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	if(isChild){
	    	getMenuInflater().inflate(R.menu.timeline, menu);
	    	doodle = menu.findItem(R.id.draw);
	    	photo = menu.findItem(R.id.photo);
	    	audio = menu.findItem(R.id.audio);
	    	video = menu.findItem(R.id.video);
	    	return super.onCreateOptionsMenu(menu);
    	} else{
    		getMenuInflater().inflate(R.menu.timeline_parent, menu);
	    	timeline = menu.findItem(R.id.timeline);
	    	new_event = menu.findItem(R.id.new_event);
	    	stats = menu.findItem(R.id.stats);
	    	//use this code to change the icon/appearance of menu item
	    	MenuItem menuItem = menu.getItem(0);
	    	menuItem.setIcon(R.drawable.timeline_selected);
	    	return super.onCreateOptionsMenu(menu);
    	}
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	//isChild = false;
    	if(isChild){
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
    	} else{
    		switch (item.getItemId()) {
		      case R.id.timeline:
		    	  //do nothing, because we are already in timeline
		    	  return true;
		      case R.id.new_event:
		    	  Context context = getApplicationContext();
		    	  CharSequence text = "Buy the full app for this feature!";
		    	  int duration = Toast.LENGTH_SHORT;
		    	  Toast toast = Toast.makeText(context, text, duration);
		    	  toast.show();
		    	  return true;
		      case R.id.stats:
		    	  recap();
		    	  return true;
		      default:
		            return super.onOptionsItemSelected(item);
		      }
    	}
	}
    
    private void recap(){
    	Intent i = new Intent(this, Recap.class);
		startActivity(i);
    }
    
    private void drawDoodle() {
    	Intent i = new Intent(this, DrawDoodle.class);
		startActivity(i);
    }
    
    private void sharePhoto() {
    	/*
    	Intent i = new Intent(this, SharePhoto.class);
		startActivity(i);
		*/
    	popupWindow();
    }
    
    private void recordAudio() {
    	Intent i = new Intent(this, RecordAudio.class);
		startActivity(i);
    }
    
    private void recordVideo() {
    	Intent i = new Intent(this, RecordVideo.class);
		startActivity(i);
    }
    
    private void addNewPost(PostEntry post, int i){
        //add this post to the scrolling linear layout
        LinearLayout posts = (LinearLayout)findViewById(R.id.posts);
        
        View child1 = LayoutInflater.from(this).inflate(
                R.layout.post, null);
        child1.setId(i);
        RelativeLayout.LayoutParams Lparams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //Lparams.addRule(RelativeLayout.BELOW, R.id.RL_default);
        //Fetch user profile pic and update (hardcoded for now)
        ImageView profile_pic = (ImageView) child1.findViewById(R.id.profile_pic);
        profile_pic.setImageResource(R.drawable.tom);
        
        //Fetch name from db and update (hardcoded)
        TextView name = (TextView) child1.findViewById(R.id.name);
        name.setText("Tom"+i);
        //Fetch time from db and update (hardcoded)
        TextView time = (TextView) child1.findViewById(R.id.time);
        time.setText("2 Hours Ago");
        //Fetch emotion
        TextView emotion = (TextView) child1.findViewById(R.id.emotion);
        emotion.setText("is feeling "+"sad");
        //Fetch description
        TextView description = (TextView) child1.findViewById(R.id.description);
        description.setText("Look at this awesome photo, Mom!");
        //Fetch content
        try {
			String filename = post.pic;
			System.out.println("filename:"+filename);
	        File f=new File(filename);
	        Bitmap bm;
			bm = BitmapFactory.decodeStream(new FileInputStream(f));
			ImageView content = (ImageView) child1.findViewById(R.id.content);
	        content.setImageBitmap(bm);
	        content.setAdjustViewBounds(true);
	        //content.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, 500));
	        content.getLayoutParams().height = 500;
	        content.setBackgroundColor(getResources().getColor(R.color.very_light_blue));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        /*
        ImageView content = (ImageView) child1.findViewById(R.id.content);
        content.setImageResource(R.drawable.playing);
        */
        //Fetch num_comments
        TextView num_comments = (TextView) child1.findViewById(R.id.num_comments);
        num_comments.setText("3"+" comments");
        
        //Lparams.setMargins(0, 0, 0, 10);
        //child1.setLayoutParams(Lparams);
        posts.addView(child1);
    }
    private class CameraOnClickListener implements OnClickListener
    {
    	
      Context myContext;
      public CameraOnClickListener(Context context) {
           this.myContext = context;
      }

      @Override
      public void onClick(View v)
      {		
    	  Intent i = new Intent();
    	  i.setClass(myContext, SharePhoto.class);
    	  i.putExtra("toCamera",true);
    	  startActivity(i);
      }

    };
    private class GalleryOnClickListener implements OnClickListener
    {

      Context myContext;
      public GalleryOnClickListener(Context context) {
           this.myContext = context;
      }

      @Override
      public void onClick(View v)
      {
    	  Intent i = new Intent(myContext, SharePhoto.class);
    	  i.putExtra("toCamera",false);
    	  startActivity(i);
      }

    };
    private void popupWindow() {
    	AlertDialog.Builder alert = new AlertDialog.Builder(this);
    	View view = View.inflate(getApplicationContext(), R.layout.activity_share_photo,null);
    	(view.findViewById(R.id.button1)).setOnClickListener(new CameraOnClickListener(this));
    	(view.findViewById(R.id.button2)).setOnClickListener(new GalleryOnClickListener(this));
    	alert.setView(view);
    	alert.show();
	}
}
