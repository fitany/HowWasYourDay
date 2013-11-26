package edu.berkeley.cs160.howwasyourday;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
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
        

       /* 
        final Button buttonBright = (Button) findViewById(R.id.imageButton5);
        final Button buttonDark = (Button) findViewById (R.id.imageButton6);
        final Button buttonRotateClockwise = (Button) findViewById (R.id.imageButton4);
        
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
        
	*/}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_comment, menu);
		return true;
	}

}
