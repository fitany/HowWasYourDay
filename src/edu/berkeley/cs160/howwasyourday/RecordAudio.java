package edu.berkeley.cs160.howwasyourday;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class RecordAudio extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_audio);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.record_audio, menu);
		return true;
	}

}
