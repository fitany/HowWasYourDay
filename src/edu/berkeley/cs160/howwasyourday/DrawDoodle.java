package edu.berkeley.cs160.howwasyourday;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

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
