package edu.berkeley.cs160.howwasyourday;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import edu.berkeley.cs160.howwasyourday.database.DatabaseHelper;

public class LoginPage extends Activity {
	
	EditText email;
	EditText password;
	DatabaseHelper db;
	SQLiteDatabase database;
	private static User user = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_page);
		
		db = new DatabaseHelper(this);
		database = db.getWritableDatabase();
		
		email = (EditText) findViewById(R.id.email);
		password= (EditText) findViewById(R.id.password);
	
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

}
