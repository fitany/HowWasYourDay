package edu.berkeley.cs160.howwasyourday.database;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import edu.berkeley.cs160.howwasyourday.PostEntry;

public class DatabaseHelper extends SQLiteOpenHelper {

	static final String dbName="DB";
	
	static final String familyTable="Families";
	static final String FamilyId="FamilyId";
	static final String FamilyName="FamilyName";
	
	static final String userTable="Users";
	static final String UserId="UserId";
	static final String UserEmail="UserEmail";
	static final String UserFirstName="UserFirstName";
	static final String UserLastName="UserLastName";
	static final String UserPassword="Password";
	static final String UserType="UserType";
	static final String UserFamilyId="UserFamilyId";
	
	static final String PostTable="Posts";
	static final String PostId="PostId";
	static final String PostUserId="PostUserId";
	static final String PostDiscription="PostDiscription";
	static final String PostPic="PostPic";
	static final String PostAudio="PostAudio";
	static final String PostVideo="PostVideo";
	static final String PostDoodle="PostDoodle";
	static final String PostFeeling="Feeling";
	static final String PostTime="PostTime";
	
	public DatabaseHelper(Context context) {
		super(context, dbName, null, 7); 
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		  // TODO Auto-generated method stub
		  
		  db.execSQL("CREATE TABLE "+familyTable+" ("+FamilyId+ " INTEGER PRIMARY KEY , "+FamilyName+ " TEXT UNIQUE)");
		  
		  db.execSQL("CREATE TABLE "+userTable+" ("+UserId+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ UserEmail +" TEXT NOT NULL UNIQUE, "+ UserPassword +" TEXT NOT NULL, " + UserFirstName +" TEXT, " + UserLastName +" TEXT, " +UserType+ " TEXT, " + UserFamilyId + " INTEGER ,FOREIGN KEY ("+UserFamilyId+") REFERENCES "+familyTable+" ("+FamilyId+"));");
		  
		  db.execSQL("CREATE TABLE "+PostTable+" ("+PostId+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ PostUserId +" INTEGER NOT NULL, "+PostDiscription+" TEXT, " + PostFeeling +" TEXT, " +PostTime+ " TEXT NOT NULL, " + PostPic + " TEXT, " + PostAudio + " TEXT, " + PostVideo + " TEXT, " + PostDoodle + " TEXT ,FOREIGN KEY ("+PostUserId+") REFERENCES "+userTable+" ("+UserId+"));");
		  
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+familyTable);
		db.execSQL("DROP TABLE IF EXISTS "+userTable);
		db.execSQL("DROP TABLE IF EXISTS "+PostTable);

	    onCreate(db);
	}
	
	public void createUser(SQLiteDatabase db, String email, String password, String firstname, String lastname, String userType) {
		ContentValues cv=new ContentValues();
	    cv.put(UserEmail, email);
	    cv.put(UserPassword, password);
	    cv.put(UserFirstName, firstname);
	    cv.put(UserLastName, lastname);
	    cv.put(UserType, userType);
	    db.insert(userTable, null, cv);
	}
	
	public Cursor findUser(SQLiteDatabase db, String email) {
		Cursor c = db.query(userTable, new String[] {UserId, UserPassword, UserFirstName, UserLastName,UserType}, UserEmail+"=?", new String[]{email}, null,null, null);
	    return c;
	}
	
	public void savePic(SQLiteDatabase db, long userID, String discription, int feeling, String path) {
		ContentValues cv=new ContentValues();
		//ByteArrayOutputStream stream = new ByteArrayOutputStream();
		//bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		System.out.println("hello1");
		cv.put(PostPic, path);
		cv.put(PostUserId, userID);
		cv.put(PostDiscription, discription);
		cv.put(PostFeeling, feeling);
		cv.put(PostTime, new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
		db.insert(PostTable, null, cv);
	}
	
	public void saveDoole(SQLiteDatabase db, int userID, String discription, int feeling, String path) {
		savePic(db, userID, discription, feeling, path);
	}
	
	public String[] findFamily(SQLiteDatabase db,long id) {
		System.out.println(id);
		Cursor c = db.query(userTable, new String[] {UserId}, UserFamilyId+"=?", new String[] {id+""}, null,null, null);
		System.out.println("Test get count in findFamily:"+c.getCount());
		String[] result = new String[c.getCount()];
		int index = 0;
		while(c.moveToNext()) {
			result[index] = c.getInt(0)+"";
		}
		
		return result;
	}
	
	public ArrayList<PostEntry> getAllPost(SQLiteDatabase db, long familyId) {
		String[] ids = findFamily(db, familyId);
		Cursor c = db.query(PostTable, new String[] {PostUserId, PostDiscription, PostFeeling, PostPic, PostDoodle, PostTime, PostAudio, PostVideo}, PostUserId+"=?", ids, null,null, PostTime);
	    ArrayList<PostEntry> posts = new ArrayList<PostEntry>();
	     
	    while(c.moveToNext()) {
	    	PostEntry post = new PostEntry(c.getInt(0), c.getInt(1), c.getString(0), c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5));
	    	posts.add(post);
	    }
	    
	    return posts;
	}
	
}
	
	
