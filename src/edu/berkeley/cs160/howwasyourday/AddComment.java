package edu.berkeley.cs160.howwasyourday;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.MeasureSpec;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import edu.berkeley.cs160.howwasyourday.database.DatabaseHelper;

public class AddComment extends Activity {
	
	DatabaseHelper db;
	SQLiteDatabase database;
	String currentPath;
	String type;
	EditText discription;
	User currentUser;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_comment);
		Bundle extras = this.getIntent().getExtras();
		currentPath = extras.getString("photo");
		type = extras.getString("type");
		

		//ActionBar actionBar = getActionBar();
        //actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //actionBar.setHomeButtonEnabled(true);
        
        //View view = View.inflate(getApplicationContext(), R.layout.action_bar_add_comment,
        //        null);
        //actionBar.setCustomView(view);
        db = new DatabaseHelper(this);
		database = db.getWritableDatabase();
		discription = (EditText) findViewById(R.id.discription);
		currentUser = LoginPage.getCurUser();
		
        if (type.equals("photo") || type.equals("doodle")) {
		    try {
		        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(currentPath));
		        ImageView iv = (ImageView) findViewById(R.id.imageView1);
		        iv.setImageBitmap(b);
		        iv.getLayoutParams().height = 400;
		        iv.getLayoutParams().width = 400;
		        iv.setBackgroundColor(Color.WHITE);
		    }
		    catch (FileNotFoundException e) 
		    {
		        e.printStackTrace();
		    }
        } else if (type.equals("audio")) {
        	
        }
        
        final ImageButton buttonBright = (ImageButton) findViewById(R.id.imageButton5);
        final ImageButton buttonDark = (ImageButton) findViewById (R.id.imageButton6);
        final ImageButton buttonRotateClockwise = (ImageButton) findViewById (R.id.imageButton4);
        
        
        buttonRotateClockwise.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	 ImageView image;
                 Bitmap bMap;
                 Matrix matrix=new Matrix();
                 
                 //Get ImageView from layout xml file
                 image = (ImageView) findViewById(R.id.imageView1);
                 image.setDrawingCacheEnabled(true);
                 image.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), 
                         MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
             image.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight()); 

             image.buildDrawingCache(true);
                 //Decode Image using Bitmap factory.
                 bMap = image.getDrawingCache();
                 image.buildDrawingCache(false);
                 matrix.postRotate(45);
                		 Bitmap bMapRotate = Bitmap.createBitmap(bMap, 0, 0,bMap.getWidth(),bMap.getHeight(), matrix, true);
                		image.setImageBitmap(bMapRotate);
            }
        });
        
        buttonBright.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	ImageView iv = (ImageView) findViewById(R.id.imageView1);
                Bitmap src = Bitmap.createBitmap(iv.getWidth(),iv.getHeight(),Bitmap.Config.ARGB_8888);//i is imageview which u want to convert in bitmap
                Canvas canvas = new Canvas(src);

                iv.draw(canvas);
            	    // image size
            	    int width = src.getWidth();
            	    int height = src.getHeight();
            	    // create output bitmap
            	    Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
            	    // color information
            	    int A, R, G, B;
            	    int pixel;
            	 
            	    // scan through all pixels
            	    for(int x = 0; x < width; ++x) {
            	        for(int y = 0; y < height; ++y) {
            	            // get pixel color
            	            pixel = src.getPixel(x, y);
            	            A = Color.alpha(pixel);
            	            R = Color.red(pixel);
            	            G = Color.green(pixel);
            	            B = Color.blue(pixel);
            	 
            	            // increase/decrease each channel
            	            R += 5;
            	            if(R > 255) { R = 255; }
            	            else if(R < 0) { R = 0; }
            	 
            	            G += 5;
            	            if(G > 255) { G = 255; }
            	            else if(G < 0) { G = 0; }
            	 
            	            B += 5;
            	            if(B > 255) { B = 255; }
            	            else if(B < 0) { B = 0; }
            	 
            	            // apply new pixel color to output bitmap
            	            bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            	        }
            	    }
            	 
            	    // return final image
            	    src = bmOut;
            	    iv.setImageBitmap(src);
            	}
        });

	
	buttonDark.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            	ImageView iv = (ImageView) findViewById(R.id.imageView1);
                Bitmap src = Bitmap.createBitmap(iv.getWidth(),iv.getHeight(),Bitmap.Config.ARGB_8888);//i is imageview whch u want to convert in bitmap
                Canvas canvas = new Canvas(src);
                iv.draw(canvas);
        	    // image size
        	    int width = src.getWidth();
        	    int height = src.getHeight();
        	    // create output bitmap
        	    Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        	    // color information
        	    int A, R, G, B;
        	    int pixel;
        	 
        	    // scan through all pixels
        	    for(int x = 0; x < width; ++x) {
        	        for(int y = 0; y < height; ++y) {
        	            // get pixel color
        	            pixel = src.getPixel(x, y);
        	            A = Color.alpha(pixel);
        	            R = Color.red(pixel);
        	            G = Color.green(pixel);
        	            B = Color.blue(pixel);
        	 
        	            // increase/decrease each channel
        	            R -= 5;
        	            if(R > 255) { R = 255; }
        	            else if(R < 0) { R = 0; }
        	 
        	            G -= 5;
        	            if(G > 255) { G = 255; }
        	            else if(G < 0) { G = 0; }
        	 
        	            B -= 5;
        	            if(B > 255) { B = 255; }
        	            else if(B < 0) { B = 0; }
        	 
        	            // apply new pixel color to output bitmap
        	            bmOut.setPixel(x, y, Color.argb(A, R, G, B));
        	        }
        	    }
        	 
        	    // return final image
        	    src = bmOut;
        	    iv.setImageBitmap(src);
        	}
    });
    
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Inflate the menu; this adds items to the action bar if it is present
		getMenuInflater().inflate(R.menu.add_comment, menu);
		return true;
	}
	
   public void newTimeline(View v) {
	   db.savePic(database, currentUser.id, discription.getText().toString(), 1, currentPath);
	   Intent intent=new Intent(this,Timeline.class);
	   startActivity(intent);
   }
   
	public void home(View v) {   
	   Intent intent=new Intent(this,Timeline.class);
	   startActivity(intent);
	}
	
	public void goBack(View v) {   
		Intent intent=new Intent(this,Timeline.class);
		startActivity(intent);
	}

}
