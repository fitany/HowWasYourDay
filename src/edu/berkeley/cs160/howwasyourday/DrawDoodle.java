package edu.berkeley.cs160.howwasyourday;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class DrawDoodle extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_draw_doodle);
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
