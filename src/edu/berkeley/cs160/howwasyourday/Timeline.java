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
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Timeline extends Activity {
	
	MenuItem doodle;
	MenuItem audio;
	MenuItem video;
	MenuItem photo;
	User currentUser;
	Boolean isChild=true;

    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_timeline);
         
        ActionBar actionBar = getActionBar();
        //actionBar.setHomeButtonEnabled(true);
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        
        currentUser = LoginPage.getCurUser();
        //userType = currentUser.type; hardcoded for now
        //if(currentUser.type.equals("kid"))
        //	isChild = true;
        //else
        //	isChild = false;
        
        View view = View.inflate(getApplicationContext(), R.layout.action_bar_timeline,
                 null);
        actionBar.setCustomView (view);
		
        //get rid of this method eventually
        /*
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
		*/
		
		//get ArrayList of posts from database, hardcoded for now
		Bitmap bm = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.playing);
        
		ArrayList<PostEntry> posts = new ArrayList<PostEntry>();
		posts.add(new PostEntry(1, 1, "doodle", bm, null, null));
		posts.add(new PostEntry(2, 1, "doodle", bm, null, null));
		posts.add(new PostEntry(1, 1, "doodle", bm, null, null));
		posts.add(new PostEntry(2, 1, "doodle", bm, null, null));
		//end get ArrayList of posts from database
		
		//Call addNewPost for every post in the ArrayList
		for(int i = 0; i < posts.size(); i++){
			PostEntry p = posts.get(i);
			addNewPost(p,i);
		}
		
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	//isChild = false;
    	if(isChild){
	    	getMenuInflater().inflate(R.menu.timeline, menu);
	    	doodle = menu.findItem(R.id.draw);
	    	photo = menu.findItem(R.id.photo);
	    	audio = menu.findItem(R.id.audio);
	    	video = menu.findItem(R.id.video);
	    	return super.onCreateOptionsMenu(menu);
    	} else{
    		getMenuInflater().inflate(R.menu.timeline_parent, menu);
	    	photo = menu.findItem(R.id.photo);
	    	audio = menu.findItem(R.id.audio);
	    	video = menu.findItem(R.id.video);
	    	//use this code to change the icon/appearance of menu item
	    	//MenuItem menuItem = menu.getItem(0);
	    	//menuItem.setIcon(R.drawable.ic_action_copy);
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
    	/*
    	RelativeLayout rl = new RelativeLayout(this);
        rl.setId(i);
        RelativeLayout.LayoutParams Lparams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
        //Lparams.addRule(RelativeLayout.BELOW, R.id.RL_default);
        Lparams.setMargins(3, 5, 3, 0);
        rl.setLayoutParams(Lparams);
        */
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
        ImageView content = (ImageView) child1.findViewById(R.id.content);
        content.setImageResource(R.drawable.playing);
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
