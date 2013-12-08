package edu.berkeley.cs160.howwasyourday;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class RecordAudio extends Activity {
	
	private static final int RESULT_CAPTURE_RECORDER_SOUND = 3;
	private String strRecorderPath = "";

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
	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if (requestCode == RESULT_CAPTURE_RECORDER_SOUND) {
//			
//		}
//	}

}
