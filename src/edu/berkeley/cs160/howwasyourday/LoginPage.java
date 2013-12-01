package edu.berkeley.cs160.howwasyourday;

import android.app.Activity;
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
	User user = null;

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
			//popup window register
		} else {
			curUser.moveToFirst();
			String pas = curUser.getString(curUser.getColumnIndex("UserPassword"));
			if (pas.equals(passwordText)) {
				//get user 
				timeLine();
			} else {
				//popup window show wrong password
			}
		}
	}
	
	public void register(View v) {
		String emailText = email.getText().toString();
		String passwordText = password.getText().toString();
	}
	
	private void timeLine() {
		Intent i = new Intent(this, Timeline.class);
		startActivity(i);
	}
	
	public User getCurUser() {
		return user;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_page, menu);
		return true;
	}

}
