package edu.berkeley.cs160.howwasyourday.database;

import java.io.ByteArrayOutputStream;
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
	static final String UserName="UserName";
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
	
	private Date date = new Date();
	
	public DatabaseHelper(Context context) {
		super(context, dbName, null, 5); 
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		  // TODO Auto-generated method stub
		  
		  db.execSQL("CREATE TABLE "+familyTable+" ("+FamilyId+ " INTEGER PRIMARY KEY , "+FamilyName+ " TEXT UNIQUE)");
		  
		  db.execSQL("CREATE TABLE "+userTable+" ("+UserId+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ UserEmail +" TEXT NOT NULL UNIQUE, "+ UserPassword +" TEXT NOT NULL, " + UserName +" TEXT, " +UserType+ " TEXT, " + UserFamilyId + " INTEGER ,FOREIGN KEY ("+UserFamilyId+") REFERENCES "+familyTable+" ("+FamilyId+"));");
		  
		  db.execSQL("CREATE TABLE "+PostTable+" ("+PostId+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ PostUserId +" INTEGER NOT NULL, "+PostDiscription+" TEXT, " + PostFeeling +" TEXT, " +PostTime+ " INTEGER NOT NULL, " + PostPic + " BLOB, " + PostAudio + " BLOB, " + PostVideo + " BLOB, " + PostDoodle + " INTEGER ,FOREIGN KEY ("+PostUserId+") REFERENCES "+userTable+" ("+UserId+"));");
		  
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+familyTable);
		db.execSQL("DROP TABLE IF EXISTS "+userTable);
		db.execSQL("DROP TABLE IF EXISTS "+PostTable);

	    onCreate(db);
	}
	
	public void createUser(SQLiteDatabase db, String email, String password, String username, String userType) {
		ContentValues cv=new ContentValues();
	    cv.put(UserEmail, email);
	    cv.put(UserPassword, password);
	    cv.put(UserName, username);
	    cv.put(UserType, userType);
	    db.insert(userTable, null, cv);
	}
	
	public Cursor findUser(SQLiteDatabase db, String email) {
		Cursor c = db.query(userTable, new String[] {UserId, UserPassword, UserName, UserType}, UserEmail+"=?", new String[]{email}, null,null, null);
	    return c;
	}
	
	public void savePic(SQLiteDatabase db, int userID, String discription, int feeling, Drawable pic) {
		ContentValues cv=new ContentValues();
		Bitmap bitmap = ((BitmapDrawable)pic).getBitmap();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		cv.put(PostPic, stream.toByteArray());
		db.insert(PostTable, null, cv);
	}
	
	public void savePic(SQLiteDatabase db, int userID, String discription, int feeling, Bitmap bitmap) {
		ContentValues cv=new ContentValues();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		cv.put(PostPic, stream.toByteArray());
		cv.put(PostUserId, userID);
		cv.put(PostDiscription, discription);
		cv.put(PostFeeling, feeling);
		cv.put(PostTime, date.getTime());
		db.insert(PostTable, null, cv);
	}
	
	public void saveDoole(SQLiteDatabase db, int userID, String discription, int feeling, Bitmap bitmap) {
		savePic(db, userID, discription, feeling, bitmap);
	}
	
	public void saveDoole(SQLiteDatabase db, int userID, String discription, int feeling, Drawable pic) {
		savePic(db, userID, discription, feeling, pic);
	}
	
	public ArrayList<PostEntry> getAllPost(SQLiteDatabase db, String[] ids) {
		Cursor c = db.query(userTable, new String[] {PostUserId, PostDiscription, PostFeeling, PostPic, PostDoodle}, PostUserId+"=?", ids, null,null, PostTime);
	    ArrayList<PostEntry> posts = new ArrayList<PostEntry>();
	    
	    while(c.moveToNext()) {
	    	byte[] pic = c.getBlob(0);
	    	Bitmap picPic = BitmapFactory.decodeByteArray(pic , 0, pic.length);
	    	byte[] doodle = c.getBlob(1);
	    	Bitmap doodlePic = BitmapFactory.decodeByteArray(doodle , 0, doodle.length);
	    	PostEntry post = new PostEntry(c.getInt(0), c.getInt(1), c.getString(0), picPic, doodlePic);
	    	posts.add(post);
	    }
	    
	    return posts;
	}
	
}
	
	
