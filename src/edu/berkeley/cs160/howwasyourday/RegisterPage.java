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
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import edu.berkeley.cs160.howwasyourday.database.DatabaseHelper;

public class RegisterPage extends Activity {
	
	private EditText email;
	private EditText password;
	private EditText lastName;
	private EditText firstName;
	private Spinner userType;
	private DatabaseHelper db;
	private SQLiteDatabase database;
	private ArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_page);
		
		db = new DatabaseHelper(this);
		database = db.getWritableDatabase();
		
		email = (EditText) findViewById(R.id.email);
		password= (EditText) findViewById(R.id.password);
		lastName = (EditText) findViewById(R.id.lastname);
		firstName = (EditText) findViewById(R.id.firstname);
		userType= (Spinner) findViewById(R.id.userType);
		
		Bundle b = getIntent().getExtras();
		String emailText = b.getString("emailText");
		if (emailText != null) {
			email.setText(emailText);
		}
		
		adapter = ArrayAdapter.createFromResource(this, R.array.User_Type, android.R.layout.simple_spinner_item); 
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		userType.setAdapter(adapter);
		userType.setVisibility(View.VISIBLE);
		userType.setPrompt("Select User Type");
		
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
		String userTypeText = userType.getSelectedItem().toString();
		if (emailText.equals("")) {
			popupWindow("Email can't be empty!");
		} else if (firstNameText.equals("")) {
			popupWindow("First Name can't be empty!");
		} else {
			db.createUser(database, emailText, passwordText,firstNameText, lastNameText, userTypeText);
			Cursor curUser = db.findUser(database, emailText);
			curUser.moveToFirst();
			User user = new User(curUser.getInt(curUser.getColumnIndex("UserId")), curUser.getString(curUser.getColumnIndex("UserFirstName")), curUser.getString(curUser.getColumnIndex("UserLastName")), curUser.getString(curUser.getColumnIndex("UserType")));
			LoginPage.setCurUser(user);
			Intent i = new Intent(this, Timeline.class);
			startActivity(i);
		}
	}
	
	public void goBack(View v) {
		Intent i = new Intent(this, LoginPage.class);
		startActivity(i);
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

}
