package edu.berkeley.cs160.howwasyourday;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class AddComment extends Activity {
	
	private Bitmap src;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_comment);
		ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        View view = View.inflate(getApplicationContext(), R.layout.action_bar_add_comment,
                null);
        actionBar.setCustomView(view);
        
<<<<<<< HEAD
        //load image
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("letters", Context.MODE_PRIVATE);
	    try {
	        File f=new File(directory, "mypic.bmp");
	        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
	        ImageView iv = (ImageView) findViewById(R.id.imageView1);
	        iv.setImageBitmap(b);
	        iv.getLayoutParams().height = 200;
	        iv.getLayoutParams().width = 200;
	        iv.setBackgroundColor(Color.WHITE);
	        
	    } 
	    catch (FileNotFoundException e) 
	    {
	        e.printStackTrace();
	    }
        
        
        final ImageButton buttonBright = (ImageButton) findViewById(R.id.imageButton5);
        final ImageButton buttonDark = (ImageButton) findViewById (R.id.imageButton6);
        final ImageButton buttonRotateClockwise = (ImageButton) findViewById (R.id.imageButton4);
        final ImageButton buttonPost = (ImageButton) findViewById (R.id.imageButton4);
=======
        final ImageButton buttonBright = (ImageButton) findViewById(R.id.imageButton5);
        final ImageButton buttonDark = (ImageButton) findViewById (R.id.imageButton6);
        final ImageButton buttonRotateClockwise = (ImageButton) findViewById (R.id.imageButton4);
>>>>>>> cffb4c6c12093be9e8ec6121386768b91d6e64aa
        
        buttonBright.setOnClickListener(new View.OnClickListener() {
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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_comment, menu);
		return true;
	}
	
	   public void newTimeline(View v) {
		   Intent intent=new Intent(this,Timeline.class);
		   intent.putExtra("NEWPIC", true);
		   startActivity(intent);
	   }

}
