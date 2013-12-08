package edu.berkeley.cs160.howwasyourday;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class DrawDoodle extends Activity {
	
	DrawArea drawArea;
	OnTouchListener touchListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_draw_doodle);
		
		drawArea = (DrawArea) findViewById(R.id.drawArea);
		
		touchListener = new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float x = event.getX();
				float y = event.getY();
				switch(event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						drawArea.moveTo(x, y);
						break;
					case MotionEvent.ACTION_MOVE:
						drawArea.lineTo(x, y);
						break;
					case MotionEvent.ACTION_UP:
						drawArea.lineTo(x, y);
						drawArea.save();
						break;
				}
				drawArea.invalidate();
				return true;
			}
		}; 
		
		drawArea.setOnTouchListener(touchListener);
		
		EditText text = (EditText)findViewById(R.id.editText);
		text.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				if (arg2.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
					String value = arg0.getText().toString();
					drawArea.text(value);
					arg0.setVisibility(TextView.INVISIBLE);
					return true;
				}
				return false;
			}
			
		});
	} 
	
	public void clear(View v) {
		drawArea.clear();
	}
	public void getColor(View v) {
		int loc = 0;
		if(v.getId()== R.id.blue_button) {
			Toast.makeText(this, "You have chosen blue.",
			Toast.LENGTH_SHORT).show();
			loc = Color.BLUE; 
		} else if (v.getId()== R.id.red_button) {
			Toast.makeText(this, "You have chosen red.",
					Toast.LENGTH_SHORT).show();
					loc = 0xf00; 
		} else if (v.getId()== R.id.green_button) {
			Toast.makeText(this, "You have chosen green.",
					Toast.LENGTH_SHORT).show();
					loc = Color.GREEN; 
		} else if (v.getId()== R.id.yellow_button) {
			Toast.makeText(this, "You have chosen yellow.",
					Toast.LENGTH_SHORT).show();
					loc = Color.YELLOW; 
			
		} else if (v.getId()== R.id.purple_button) {
			Toast.makeText(this, "You have chosen purple.",
					Toast.LENGTH_SHORT).show();
					loc = 0xd1a5ea; 
		} else if (v.getId()== R.id.Eraser) {
			Toast.makeText(this, "You have chosen eraser.",
					Toast.LENGTH_SHORT).show();
					loc = Color.WHITE; 
		}  else {
		
		}
		drawArea.setColor(loc);
	}
	
	public void getStroke(View v) {
		int size = 10;
		if (v.getId()== R.id.Pencil) {
			Toast.makeText(this, "You have chosen pencil.",
					Toast.LENGTH_SHORT).show();
			size = 3;
		} else if (v.getId()== R.id.Brush) {
			Toast.makeText(this, "You have chosen brush.",
					Toast.LENGTH_SHORT).show();
			size= 10;
		}  else {
			
		}
		drawArea.setSize(size);
	}
	
	public void showText (View v) {
		if (v.getId()== R.id.Text) {
			Toast.makeText(this, "You have chosen text.",
					Toast.LENGTH_SHORT).show();
		}
		EditText text = (EditText)findViewById(R.id.editText);
		text.setVisibility(View.VISIBLE);

	}
	
	public void done(View v){
		ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("letters", Context.MODE_PRIVATE);
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/howwasyourday/");
		storageDir.mkdirs();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mypath=new File(directory,"mypic.bmp");
        FileOutputStream fos = null;
        
        try {           
            fos = new FileOutputStream(mypath);
            drawArea.saveBitmap(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		Intent i = new Intent(this, AddComment.class);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.draw_doodle, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.timeline) {
			Intent i = new Intent(this, Timeline.class);
			startActivity(i);
		}
		return true;
	}
	
	

}
