package edu.berkeley.cs160.howwasyourday;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import edu.berkeley.cs160.howwasyourday.database.DatabaseHelper;

public class Recap extends Activity{
	LinearLayout pieContainer;
    LinearLayout pane;
    LinearLayout pane2;
    private PieView pvemo;
    private PieView pvtype;
    ArrayList<Integer> aLIst = new ArrayList<Integer>();
    private Button button;
    Spinner spinner;
	DatabaseHelper db;
	SQLiteDatabase database;
	ArrayList<Integer> type = new ArrayList<Integer>();
	String[] kidNameArray;
	String kidName;
	User currentUser;
	ArrayList<Integer> userid = new ArrayList<Integer>();
	ArrayList<String> username = new ArrayList<String>();
	
	int numOfPic = 0;
	int numOfDoodle = 0;
	int numOfAudio = 0;
	int numOfVideo = 0;
	
	int numOfNormal = 0;
	int numOfHappy = 0;
	int numOfSad = 0;
	int numOfShocked = 0;
	int numOfTears = 0;
	int numOfBlush = 0;
	int numOfDelighted = 0;
	int numOfMeep = 0;
	int numOfSmart = 0;
	int numOfCool = 0;
	int numOfMad = 0;
	
    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recap);
		
        db = new DatabaseHelper(this);
		database = db.getWritableDatabase();
		
		currentUser = LoginPage.getCurUser();
		Cursor kids = db.findKids(database, currentUser.familyId);
		while(kids.moveToNext()) {
	    	int id = kids.getInt(kids.getColumnIndex("UserId"));
	    	userid.add(id);
	    	String name = kids.getString(kids.getColumnIndex("UserFirstName"));
	    	username.add(name);
	    }
		
		
		final Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		//spinner.setAdapter(new MyAdapter(this, R.layout.recap, username));
		// Specify the layout to use when the list of choices appears
		//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		//spinner.setAdapter(adapter);
		/**
		final Spinner mySpinner = (Spinner)findViewById(R.id.feeling);
        mySpinner.setAdapter(new MyAdapter(this, R.layout.feeling_list, feelings));
        
        feelingBtn.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
           	 	mySpinner.performClick();
           }
        });
        
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View v, int position, long id) {
				// TODO Auto-generated method stub
				feeling = position;
				feelingBtn.setImageResource(images[position]);
				feelingsText.setText("Feeling " + feelings[position]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
        	
        });**/
		int[] useridarray = convertArrayList(userid);
		String[] usernamearray = convertArrayListS(username);
		if (usernamearray.length != 0){
			addListenerOnButton();
			addListenerOnSpinnerItemSelection();
			Log.e("MK", "kidName start to selected" + kidName);
			kidName = spinner.getSelectedItem().toString();
			Log.e("MK", "kidName selected");
			int kidId = 0;
			
		
			int index = getIndex(kidName, usernamearray);
			kidId = useridarray[index];
			
			getKidStats(kidId);
			
			pane = (LinearLayout) findViewById(R.id.pane);
			//pane2 = (LinearLayout) findViewById(R.id.pane2);
	
		    pvemo = new PieView(this, aLIst);
		    pvtype = new PieView(this, type);
		    pane.addView(pvemo);
		    pane2.addView(pvtype);
		} else {
			Toast.makeText(Recap.this,
					"You have no child registered for the family", Toast.LENGTH_SHORT).show();
		}
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
		    spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		  }

    
    public void getKidStats(long kidId){
    	System.out.println("In getKidStats");
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
    	type.add(numOfPic);
    	type.add(numOfDoodle);
    	type.add(numOfAudio);
    	type.add(numOfVideo);
    	
    	for (PostEntry post : posts) {
    		System.out.println("Feeling:"+post.feeling);
    		if (post.feeling == 0) {
				//numOfNone++;
    		} else if (post.feeling == 1) {
    			numOfHappy++;
    		} else if (post.feeling == 2) {
    			numOfShocked++;
    		} else if (post.feeling == 3) {
    			numOfTears++;
    		} else if (post.feeling == 4) {
    			numOfBlush++;
    		} else if (post.feeling == 5) {
    			numOfDelighted++;
    		}	else if (post.feeling == 6) {
    			numOfMeep++;
    		} else if (post.feeling == 7) {
    			numOfSmart++;
    			System.out.println("NumOfSmart:"+numOfSmart);
    		} else if (post.feeling == 8) {
    			numOfSad++;
    		} else if (post.feeling == 9) {
    			numOfCool++;
    		} else {
    			numOfMad++;
    		}
    	}
    	aLIst.add(numOfHappy);
    	aLIst.add(numOfShocked);
    	aLIst.add(numOfTears);
    	aLIst.add(numOfBlush);
    	aLIst.add(numOfDelighted);
    	aLIst.add(numOfMeep);
    	aLIst.add(numOfSmart);
    	aLIst.add(numOfSad);
    	aLIst.add(numOfCool);
    	aLIst.add(numOfMad);
    	
    }
    
    public static int[] convertArrayList(ArrayList<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = iterator.next().intValue();
        }
        return ret;
    }
    
    public static String[] convertArrayListS(ArrayList<String> integers)
    {
        String[] ret = new String[integers.size()];
        Iterator<String> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = iterator.next().toString();
        }
        return ret;
    }
    
    public static int getIndex(String a, String[] array) {
    	int index = 0;
    	for (int i=0;i<array.length-1;i++) {
    		if (array[i] == a) {
    			index = i;
    		}
    	}
    	return index;
    }
	
		
}