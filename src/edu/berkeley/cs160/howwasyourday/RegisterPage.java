package edu.berkeley.cs160.howwasyourday;

import edu.berkeley.cs160.howwasyourday.database.DatabaseHelper;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class RegisterPage extends Activity {
	
	private EditText email;
	private EditText password;
	private EditText lastName;
	private EditText firstName;
	private EditText userType;
	private DatabaseHelper db;
	private SQLiteDatabase database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_page);
		
		db = new DatabaseHelper(this);
		database = db.getWritableDatabase();
		
		email = (EditText) findViewById(R.id.email);
		password= (EditText) findViewById(R.id.password);
		lastName = (EditText) findViewById(R.id.lastname);
		lastName = (EditText) findViewById(R.id.firstname);
		userType= (EditText) findViewById(R.id.userType);
		
		Bundle b = getIntent().getExtras();
		String emailText = b.getString("emailText");
		if (emailText != null) {
			email.setText(emailText);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_page, menu);
		return true;
	}
	
	
	public void done(View v) {
		String emailText = email.getText().toString();
		String passwordText = password.getText().toString();
		String lastNameText = lastName.getText().toString();
		String firstNameText = firstName.getText().toString();
		String userTypeText = userType.getText().toString();
		db.createUser(database, emailText, passwordText,lastNameText, userTypeText);
		Cursor curUser = db.findUser(database, emailText);
		curUser.moveToFirst();
		User user = new User(curUser.getInt(curUser.getColumnIndex("UserId")), curUser.getString(curUser.getColumnIndex("UserName")), curUser.getString(curUser.getColumnIndex("UserType")));
		LoginPage.setCurUser(user);
		Intent i = new Intent(this, Timeline.class);
		startActivity(i);
	}

}
