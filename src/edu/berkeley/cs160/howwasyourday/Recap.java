package edu.berkeley.cs160.howwasyourday;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class Recap extends Activity{
	LinearLayout pieContainer;
    LinearLayout pane;
    private PieView pv;
    ArrayList<Integer> aLIst = new ArrayList<Integer>();
    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_draw_doodle);
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.planets_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
		pane = (LinearLayout) findViewById(R.id.pane);

	    aLIst.add(200);
	    aLIst.add(300);
	    aLIst.add(150);
	    aLIst.add(400);

	    pv = new PieView(this, aLIst);
	    pane.addView(pv);
	}
	
	
		
}