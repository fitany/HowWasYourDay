package edu.berkeley.cs160.howwasyourday;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import edu.berkeley.cs160.howwasyourday.database.DatabaseHelper;

public class Recap extends Activity{
	LinearLayout pieContainer;
    LinearLayout pane;
    LinearLayout pane2;
    private PieView pvemo;
    private PieView pvtype;
    ArrayList<Integer> aLIst = new ArrayList<Integer>();
    Spinner spinner;
	DatabaseHelper db;
	SQLiteDatabase database;
	ArrayList<Integer> type = new ArrayList<Integer>();
	String[] kidNameArray;
	String kidName;
	User currentUser;
	ArrayList<Integer> userid = new ArrayList<Integer>();
	ArrayList<String> username = new ArrayList<String>();
	int[] useridarray;
	String[] usernamearray;
	TextView emotions;
	TextView types;
    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
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
		
		
		spinner = (Spinner) findViewById(R.id.kid);
		pane = (LinearLayout) findViewById(R.id.pane);
		pane2 = (LinearLayout) findViewById(R.id.pane2);
		emotions = (TextView) findViewById(R.id.emotions);
		types = (TextView) findViewById(R.id.types);
		
		useridarray = convertArrayList(userid);
		usernamearray = convertArrayListS(username);
		
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, usernamearray);
		spinner.setAdapter(spinnerArrayAdapter);
		spinner.setPrompt("Choose a Child:");
		
		
		if (usernamearray.length == 0){
			Toast.makeText(Recap.this,
					"You have no child registered for the family", Toast.LENGTH_SHORT).show();
		} else {
		
			spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	
				@Override
				public void onItemSelected(AdapterView<?> arg0, View v, int position, long id) {
					// TODO Auto-generated method stub
					updatePie();
				}
	
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				}
	        	
	        });
		}
	}
	
	private void updatePie() {
		String kidName = spinner.getSelectedItem().toString();
		int index = getIndex(kidName, usernamearray);
		if (index >= 0) {
			int kidId = useridarray[index];
			getKidStats(kidId);
	
		    pvemo = new PieView(this, aLIst);
		    pvtype = new PieView(this, type);
		    System.out.println(aLIst);
		    pane.removeAllViews();
		    pane2.removeAllViews();
		    pane.addView(pvemo);
		    pane2.addView(pvtype);
		    aLIst = new ArrayList<Integer>();
		    type = new ArrayList<Integer>();
		}	
	}
    
    public void getKidStats(long kidId){
    	/** TODO: get the number of emos and types of the posts made by the kid "kidName"
    	int happy = get the number of happy emos;
    	int sad = get the number of happy emos;
    	int angry = //get the number of happy emos;
    	**/
    	int numOfPic = 0;
    	int numOfDoodle = 0;
    	int numOfAudio = 0;
    	int numOfVideo = 0;
    	
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

    	ArrayList<PostEntry> posts = db.getAllPostFromIndividual(database, kidId);
    	for (PostEntry post : posts) {
    		if (post.pic != null) {
    			numOfPic++;
    		} else if (post.doodle != null) {
    			numOfDoodle++;
    		} else if (post.audio != null) {
    			numOfAudio++;
    		} else {
    			numOfVideo++;
    		}
    	}
    	type.add(numOfPic);
    	type.add(numOfDoodle);
    	type.add(numOfAudio);
    	type.add(numOfVideo);
    	
    	String posttype = types.getText().toString() + "\n"+"\n";
    	posttype+= "Photos: " + numOfPic + "\n";
    	posttype+= "Doodles: " + numOfDoodle + "\n";
    	posttype+= "Audios: " + numOfAudio + "\n";
    	posttype+= "Videos: " + numOfVideo + "\n";
    	
    	types.setText(posttype);
    	
    	for (PostEntry post : posts) {
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
    	
    	String postemotions = emotions.getText().toString() + "\n"+"\n";
    	if (numOfHappy != 0) {
    		postemotions+= "Happy: " + numOfHappy + "\n";
    	} 
    	if (numOfShocked != 0) {
    		postemotions+= "Shocked: " + numOfShocked + "\n";
    	} 
    	if (numOfTears != 0) {
    		postemotions+= "Tears: " + numOfTears + "\n";
    	}
    	if (numOfBlush != 0) {
    		postemotions+= "Blush: " + numOfBlush + "\n";
    	} 
    	if (numOfDelighted != 0) {
    		postemotions+= "Delighted: " + numOfDelighted + "\n";
    	}
    	if (numOfMeep != 0) {
    		postemotions+= "Meep: " + numOfMeep + "\n";
    	} 
    	if (numOfSmart != 0) {
    		postemotions+= "Smart: " + numOfSmart + "\n";
    	} 
    	if (numOfSad != 0) {
    		postemotions+= "Sad: " + numOfSad + "\n";
    	} 
    	if (numOfCool != 0) {
    		postemotions+= "Cool: " + numOfCool + "\n";
    	} 
    	if (numOfMad != 0) {
    		postemotions+= "Mad: " + numOfMad + "\n";
    	} 
    	emotions.setText(postemotions);
    	
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
    	for (int i=0;i<array.length;i++) {
    		if (array[i].equals(a)) {
    			return i;
    		}
    	}
    	return -1;
    }
	
		
}