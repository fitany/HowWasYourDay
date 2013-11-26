package edu.berkeley.cs160.howwasyourday;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
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
					loc = Color.RED; 
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
					loc = Color.parseColor("purple"); 
		} else if (v.getId()== R.id.Eraser) {
			Toast.makeText(this, "You have chosen eraser.",
					Toast.LENGTH_SHORT).show();
					loc = Color.WHITE; 
		} else {
		
		}
		
		drawArea.setColor(loc);
	}
	
	public void done(View v){
		Intent i = new Intent(this, Post.class);
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
