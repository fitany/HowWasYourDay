package edu.berkeley.cs160.howwasyourday;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Timeline extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.timeline, menu);
        //return super.onCreateOptionsMenu(menu);
    	getMenuInflater().inflate(R.menu.timeline, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    
}
