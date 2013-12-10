package edu.berkeley.cs160.howwasyourday;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import edu.berkeley.cs160.howwasyourday.database.DatabaseHelper;

public class RegisterPage extends Activity implements TextWatcher {
	
	private EditText email;
	private EditText password;
	private EditText lastName;
	private EditText firstName;
	private Spinner userType;
	private DatabaseHelper db;
	private SQLiteDatabase database;
	private ArrayAdapter adapter;
	private Button register;
	AutoCompleteTextView autoComplete;
	Family[] families;
	String[] familyNames;
	TextView textViewSelection;
	TextView createNewFamily;
	EditText name;

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
		register = (Button) findViewById(R.id.register);
		createNewFamily = (TextView) findViewById(R.id.newFamily);
		createNewFamily.setVisibility(View.INVISIBLE);
		
		autoComplete = (AutoCompleteTextView) findViewById(R.id.family);
        autoComplete.addTextChangedListener(this);
		
		updateFamilies();
		
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
		
		userType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View v, int position, long id) {
				// TODO Auto-generated method stub
				if (position == 1) {
					createNewFamily.setVisibility(View.VISIBLE);
				} else {
					createNewFamily.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
        	
        });
		
		register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String emailText = email.getText().toString();
				String passwordText = password.getText().toString();
				String lastNameText = lastName.getText().toString();
				String firstNameText = firstName.getText().toString();
				String userTypeText = userType.getSelectedItem().toString();
				String familyNameText = autoComplete.getText().toString();
				if (emailText.equals("")) {
					popupWindow("Email can't be empty!");
				} else if (firstNameText.equals("")) {
					popupWindow("First Name can't be empty!");
				} else {
					int pos = findFamily(familyNameText);
					if (userTypeText.equals("Children")) {
						if (pos >= 0) {
							db.createUser(database, emailText, passwordText,firstNameText, lastNameText, userTypeText, families[pos].id);
							Cursor curUser = db.findUser(database, emailText);
							curUser.moveToFirst();
							User user = new User(curUser.getInt(curUser.getColumnIndex("UserId")), curUser.getString(curUser.getColumnIndex("UserFirstName")), curUser.getString(curUser.getColumnIndex("UserLastName")), curUser.getString(curUser.getColumnIndex("UserType")));
							LoginPage.setCurUser(user);
							done();
							
						} else {
							popupWindow("No Family Record Found!");
						}
					} else {
						if (pos >= 0) {
							db.createUser(database, emailText, passwordText,firstNameText, lastNameText, userTypeText, families[pos].id);
							Cursor curUser = db.findUser(database, emailText);
							curUser.moveToFirst();
							User user = new User(curUser.getInt(curUser.getColumnIndex("UserId")), curUser.getString(curUser.getColumnIndex("UserFirstName")), curUser.getString(curUser.getColumnIndex("UserLastName")), curUser.getString(curUser.getColumnIndex("UserType")));
							LoginPage.setCurUser(user);
							done();
						} else {
							popupWindow("No Family Record Found! Please Register First");
						}
					}
				}
			}
		});
		
		createNewFamily.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				register();
			}
			
		});
		
	}
	
	private void updateFamilies() {
		families = db.getAllFamilies(database);
		familyNames = new String[families.length];
		int index = 0;
		for (Family family: families) {
			familyNames[index] = family.name;
			index++;
		}
		
		autoComplete.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, familyNames));
	}
	
	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_page, menu);
		return true;
	}
	
	public int findFamily(String name) {
		for (int i =0; i < familyNames.length; i++) {
			if (name.equals(familyNames[i])) {
				return i;
			}
		}
		
		return -1;
	}
	
	public void done() {
		Intent i = new Intent(this, Timeline.class);
		startActivity(i);
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
	
	private void register() {
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		LayoutInflater inflater = this.getLayoutInflater();
		View layout=inflater.inflate(R.layout.new_family,null);
		alertDialogBuilder.setView(layout);
		
		name =(EditText) layout.findViewById(R.id.familyname);
		
		alertDialogBuilder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	               // User clicked OK button
	        	   db.createFamily(database, name.getText().toString());
	        	   autoComplete.setText(name.getText().toString());
	        	   updateFamilies();
	        	   dialog.dismiss();
	           }
	       });
		alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	               // User cancelled the dialog
	        	   dialog.cancel();
	           }
	       });

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
		
	}

}
