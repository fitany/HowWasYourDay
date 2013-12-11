package edu.berkeley.cs160.howwasyourday;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
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
	String[] feelings={"Normal", "Happy", "Sad", "Shocked","Tears","Blush", "Delighted", "Meep", "Smart", "Cool", "Mad"};
	
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        
        currentUser = LoginPage.getCurUser();
        if(currentUser.type != null && currentUser.type.equals("Child"))
        	isChild = true;
        else
        	isChild = false;
        
        View view = View.inflate(getApplicationContext(), R.layout.action_bar_timeline, null);
        TextView family = (TextView) view.findViewById(R.id.textView1);
        family.setText("The "+currentUser.lastname+" Family");
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
		for(int i = posts.size()-1; i > -1; i--){
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
		    	  CharSequence text = "Buy the full app for this feature!";
		    	  int duration = Toast.LENGTH_SHORT;
		    	  Toast toast = Toast.makeText(this, text, duration);
		    	  toast.show();
		    	  return true;
		    	  //recordVideo();
		    	  //return true;
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
		    	  CharSequence text = "Buy the full app for this feature!";
		    	  int duration = Toast.LENGTH_SHORT;
		    	  Toast toast = Toast.makeText(this, text, duration);
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
        //Fetch user profile pic and update, hardcoded to switch off between tom and ana
        ImageView profile_pic = (ImageView) child1.findViewById(R.id.profile_pic);
        if(post.userID%2==0)
        	profile_pic.setImageResource(R.drawable.ana);
        else
        	profile_pic.setImageResource(R.drawable.tom);
        //Fetch name from db and update
        TextView name = (TextView) child1.findViewById(R.id.name);
        User post_user = db.findUser(database, (long)post.userID);
        name.setText(post_user.firstname);
        //name.setText("Tom"+i);
        //Fetch time from db and update
        TextView time = (TextView) child1.findViewById(R.id.time);
        time.setText(getTimeAgo(post.time));
        //Fetch emotion
        TextView emotion = (TextView) child1.findViewById(R.id.emotion);
        int feeling = post.feeling;
        ImageView face = (ImageView) child1.findViewById(R.id.face);
        if(feeling==0){
        	emotion.setText("");    	
        	face.setVisibility(View.INVISIBLE);
        }
        else{
        	switch(feeling){
	        	case 1://happy
	        		face.setImageResource(R.drawable.happy);
	        	case 2://sad
	        		face.setImageResource(R.drawable.sad);
	        		break;
	        	case 3://shocked
	        		face.setImageResource(R.drawable.shocked);
	        		break;
	        	case 4://tears
	        		face.setImageResource(R.drawable.tears);
	        		break;
	        	case 5://blush
	        		face.setImageResource(R.drawable.blush);
	        		break;
	        	case 6://delighted
	        		face.setImageResource(R.drawable.delighted);
	        		break;
	        	case 7://meep
	        		face.setImageResource(R.drawable.meep);
	        		break;
	        	case 8://smart
	        		face.setImageResource(R.drawable.smart);
	        		break;
	        	case 9://cool
	        		face.setImageResource(R.drawable.cool);
	        		break;
	        	case 10://mad
	        		face.setImageResource(R.drawable.mad);
	        		break;
        	}
        }
        //Fetch description
        TextView description = (TextView) child1.findViewById(R.id.description);
        description.setText(post.discription);
        //description.setText("Look at this awesome photo, Mom!");
        //Fetch content
        //try {
			String filename = post.pic;
			if (filename == null) {
				filename = post.doodle;
			}
			if (filename == null) {
				filename = post.audio;
			}		
			if (filename == null) {
				filename = post.video;
			}
			if(filename != null && filename.indexOf(".amr")==-1){
		        File f=new File(filename);
		        //Bitmap bm;
				//bm = BitmapFactory.decodeStream(new FileInputStream(f));
				ImageView content = (ImageView) child1.findViewById(R.id.content);
		        //content.setImageBitmap(bm);
		        content.setImageBitmap(
		        	    decodeSampledBitmap(filename, 100, 100));
		        content.setAdjustViewBounds(true);
		        //content.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, 500));
		        content.getLayoutParams().height = 500;
		        if(filename.indexOf("/Doodle/") != -1){
		        	content.getLayoutParams().width = 500;
		        	content.setBackgroundColor(getResources().getColor(R.color.white));
		        }
		        else
		        	content.setBackgroundColor(getResources().getColor(R.color.very_light_blue));
			} else if(filename != null){
				ImageButton content = (ImageButton) child1.findViewById(R.id.content);
		        content.setImageResource(R.drawable.play_icon);
		        content.setAdjustViewBounds(true);
		        content.getLayoutParams().height = 500;
		        content.getLayoutParams().width = 500;
	        	content.setOnClickListener(new AudioOnClickListener(filename));
			}
		//} catch (FileNotFoundException e) {
			//e.printStackTrace();
		//}
        /*
        ImageView content = (ImageView) child1.findViewById(R.id.content);
        content.setImageResource(R.drawable.playing);
        */
        //Fetch num_comments, 0 for now
        //TextView num_comments = (TextView) child1.findViewById(R.id.num_comments);
        //num_comments.setText(0+" comments");
        
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
    private String getTimeAgo(String time){
    	int post_day = Integer.parseInt(time.substring(time.indexOf('_')-2,time.indexOf('_')));
        int post_hours = Integer.parseInt(time.substring(time.indexOf('_')+1,time.indexOf('_')+3));
        int post_minutes = Integer.parseInt(time.substring(time.indexOf('_')+3,time.indexOf('_')+5));
        Calendar c = Calendar.getInstance(); 
        int minutes = c.get(Calendar.MINUTE);
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int days = c.get(Calendar.DAY_OF_MONTH);
        
        if(days-post_day==0){
        	if(hours-post_hours==0){
        		if(minutes-post_minutes==0){
        			return "Just Now";
        		}
        		if(minutes-post_minutes==1)
        			return minutes-post_minutes + " minute ago";
        		else
        			return minutes-post_minutes + " minutes ago";
        	}
        	if(hours-post_hours==1)
        		return hours-post_hours + " hour ago";
        	else
        		return hours-post_hours + " hours ago";
        }
        if(days-post_day==1)
        	return days-post_day + " day ago";
        else
        	return days-post_day + " days ago";
    }
    private void playMusic(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "audio");
        startActivity(intent);
    }
    private class AudioOnClickListener implements OnClickListener{
    	String myAudioFile;
    	public AudioOnClickListener(String audioFile){
    		myAudioFile=audioFile;
    	}
    	@Override
	    public void onClick(View v) {
	    	File playfile = new File(myAudioFile);
        	playMusic(playfile);
	    }
    }
    public static Bitmap decodeSampledBitmap(String file,
            int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file, options);
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) {
	
	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;
	
	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }
	    return inSampleSize;
    }
}
