package edu.berkeley.cs160.howwasyourday;

import java.util.ArrayList;

import edu.berkeley.cs160.howwasyourday.database.DatabaseHelper;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class Recap extends Activity{
	LinearLayout pieContainer;
    LinearLayout pane;
    private PieView pv;
    ArrayList<Integer> aLIst = new ArrayList<Integer>();
    private Button button;
    Spinner spinner;
	DatabaseHelper db;
	SQLiteDatabase database;
	
	int numOfPic = 0;
	int numOfDoodle = 0;
	int numOfAudio = 0;
	int numOfVideo = 0;
    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recap);
		
        db = new DatabaseHelper(this);
		database = db.getWritableDatabase();
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.kids_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
		addListenerOnButton();
		addListenerOnSpinnerItemSelection();

		
		
		pane = (LinearLayout) findViewById(R.id.pane);

	    pv = new PieView(this, aLIst);
	    pane.addView(pv);
	}
	
	public void addListenerOnButton() {
		    button = (Button) findViewById(R.id.spinner_button);
		    button.setOnClickListener(new OnClickListener() {
		      @Override
		      public void onClick(View v) {
		        
		      }
		    });
		  }

	public void addListenerOnSpinnerItemSelection() {
		    spinner = (Spinner) findViewById(R.id.spinner1);
		    //spinner.setOnItemSelectedListener(new OnItemSelectedListener());
		  }

	public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
    
    public void getKidStats(long kidId){
    	/** TODO: get the number of emos and types of the posts made by the kid "kidName"
    	int happy = get the number of happy emos;
    	int sad = get the number of happy emos;
    	int angry = //get the number of happy emos;
    	**/
//    	aLIst.add(happy);
//	    aLIst.add(sad);
//	    aLIst.add(angry);
    	ArrayList<PostEntry> posts = db.getAllPostFromIndividual(database, kidId);
    	
    	for (PostEntry post : posts) {
    		if (!post.pic.equals("")) {
    			numOfPic++;
    		} else if (!post.doodle.equals("")) {
    			numOfDoodle++;
    		} else if (!post.audio.equals("")) {
    			numOfAudio++;
    		} else {
    			numOfVideo++;
    		}
    	}
    }
	
		
}