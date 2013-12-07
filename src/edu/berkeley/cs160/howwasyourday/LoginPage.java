package edu.berkeley.cs160.howwasyourday;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.ViewFlipper;
import edu.berkeley.cs160.howwasyourday.database.DatabaseHelper;

public class LoginPage extends Activity {
	
	EditText email;
	EditText password;
	DatabaseHelper db;
	SQLiteDatabase database;
	private static User user = null;
	private ViewFlipper viewFlipper;
    private float lastX;
    private ImageView[] dots;
    private int currDotIndex;
    private int numDots;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_page);
		
		db = new DatabaseHelper(this);
		database = db.getWritableDatabase();
		
		email = (EditText) findViewById(R.id.email);
		password= (EditText) findViewById(R.id.password);
		
		viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper1);
		numDots = 5;
		dots = new ImageView[5];
		dots[0]=(ImageView) findViewById(R.id.dot1);
		dots[1]=(ImageView) findViewById(R.id.dot2);
		dots[2]=(ImageView) findViewById(R.id.dot3);
		dots[3]=(ImageView) findViewById(R.id.dot4);
		dots[4]=(ImageView) findViewById(R.id.dot5);
		selectDot(dots[0]);
		currDotIndex = 0;
	}
	
	public void login(View v) {
		String emailText = email.getText().toString();
		String passwordText = password.getText().toString();
		Cursor curUser = db.findUser(database, emailText);
		
		
		if (curUser.moveToFirst() == false) {
			popupWindow("No Email Found, Please register first!");
		} else {
			String pas = curUser.getString(curUser.getColumnIndex("Password"));
			if (pas.equals(passwordText)) {
				user = new User(curUser.getInt(curUser.getColumnIndex("UserId")), curUser.getString(curUser.getColumnIndex("UserName")), curUser.getString(curUser.getColumnIndex("UserType")));
				timeLine();
			} else {
				popupWindow("Wrong password, please try again!");
			}
		}
	}
	
	public void register(View v) {
		String emailText = email.getText().toString();
		Cursor curUser = db.findUser(database, emailText);
		if (curUser.getCount() > 0) {
			popupWindow("Email already existed!");
		} else {
			Intent i = new Intent(this, RegisterPage.class);
			i.putExtra("emailText", emailText);
			startActivity(i);
		}
	}
	
	private void popupWindow(String message) {
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		
		alertDialogBuilder
		.setMessage(message)
		.setCancelable(false)
		.setNegativeButton("OK",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, just close
				// the dialog box and do nothing
				dialog.cancel();
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
		
	}
	
	private void timeLine() {
		Intent i = new Intent(this, Timeline.class);
		startActivity(i);
	}
	
	public static User getCurUser() {
		return user;
	}
	
	public static void setCurUser(User newUser) {
		user = newUser;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_page, menu);
		return true;
	}
	
	 // Method to handle touch event like left to right swap and right to left swap
    public boolean onTouchEvent(MotionEvent touchevent) 
    {
                 switch (touchevent.getAction())
                 {
                        // when user first touches the screen to swap
                         case MotionEvent.ACTION_DOWN: 
                         {
                             lastX = touchevent.getX();
                             break;
                        }
                         case MotionEvent.ACTION_UP: 
                         {
                             float currentX = touchevent.getX();
                             
                             // if left to right swipe on screen
                             if (lastX < currentX) 
                             {
                                  // If no more View/Child to flip
                                 if (viewFlipper.getDisplayedChild() == 0)
                                     break;
                                 
                                 // set the required Animation type to ViewFlipper
                                 // The Next screen will come in form Left and current Screen will go OUT from Right 
                                 viewFlipper.setInAnimation(this, R.anim.in_from_left);
                                 viewFlipper.setOutAnimation(this, R.anim.out_to_right);
                                 // Show the next Screen
                                 viewFlipper.showNext();
                                 unselectDot(dots[currDotIndex]);
                                 if(currDotIndex > 0)
                                	 currDotIndex--;
                                 selectDot(dots[currDotIndex]);
                             }
                             
                             // if right to left swipe on screen
                             if (lastX > currentX)
                             {
                                 if (viewFlipper.getDisplayedChild() == 1)
                                     break;
                                 // set the required Animation type to ViewFlipper
                                 // The Next screen will come in form Right and current Screen will go OUT from Left 
                                 viewFlipper.setInAnimation(this, R.anim.in_from_right);
                                 viewFlipper.setOutAnimation(this, R.anim.out_to_left);
                                 // Show The Previous Screen
                                 viewFlipper.showPrevious();
                                 unselectDot(dots[currDotIndex]);
                                 if(currDotIndex < numDots)
                                	 currDotIndex++;
                                 selectDot(dots[currDotIndex]);
                             }
                             break;
                         }
                 }
                 return false;
    }
    public void selectDot(ImageView dot){
    	dot.setColorFilter(Color.BLUE);
    }
    public void unselectDot(ImageView dot){
    	dot.setColorFilter(Color.TRANSPARENT);
    }
    

}
