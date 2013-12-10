package edu.berkeley.cs160.howwasyourday.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import edu.berkeley.cs160.howwasyourday.Family;
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
		super(context, dbName, null, 13); 
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		  // TODO Auto-generated method stub
		  
		  db.execSQL("CREATE TABLE "+familyTable+" ("+FamilyId+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+FamilyName+ " TEXT UNIQUE)");
		  
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
	
	public void createFamily(SQLiteDatabase db, String name) {
		ContentValues cv=new ContentValues();
	    cv.put(FamilyName, name);
	    db.insert(familyTable, null, cv);
	}
	
	public void createUser(SQLiteDatabase db, String email, String password, String firstname, String lastname, String userType, long familyId) {
		ContentValues cv=new ContentValues();
	    cv.put(UserEmail, email);
	    cv.put(UserPassword, password);
	    cv.put(UserFirstName, firstname);
	    cv.put(UserLastName, lastname);
	    cv.put(UserType, userType);
	    cv.put(UserFamilyId, familyId);
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
		cv.put(PostPic, path);
		cv.put(PostUserId, userID);
		cv.put(PostDiscription, discription);
		cv.put(PostFeeling, feeling);
		cv.put(PostTime, new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
		db.insert(PostTable, null, cv);
	}
	
	public void saveDoole(SQLiteDatabase db, int userID, String discription, int feeling, String path) {
		ContentValues cv=new ContentValues();
		cv.put(PostDoodle, path);
		cv.put(PostUserId, userID);
		cv.put(PostDiscription, discription);
		cv.put(PostFeeling, feeling);
		cv.put(PostTime, new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
		db.insert(PostTable, null, cv);
	}
	
	public void saveAudio(SQLiteDatabase db, int userID, String discription, int feeling, String path) {
		ContentValues cv=new ContentValues();
		cv.put(PostAudio, path);
		cv.put(PostUserId, userID);
		cv.put(PostDiscription, discription);
		cv.put(PostFeeling, feeling);
		cv.put(PostTime, new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
		db.insert(PostTable, null, cv);
	}
	
	public String[] findFamily(SQLiteDatabase db,long id) {
		Cursor c = db.query(userTable, new String[] {UserId}, UserFamilyId+"=?", new String[] {id+""}, null,null, null);
		String[] result = new String[c.getCount()];
		int index = 0;
		while(c.moveToNext()) {
			result[index] = c.getInt(0)+"";
			index++;
		}
		
		return result;
	}
	
	public Cursor findKids(SQLiteDatabase db,long id) {
		Cursor c = db.rawQuery("select UserId, UserFirstName from Users where UserFamilyId = ? AND UserType = ?", new String[] { id+"", "Children" });
		return c;
	}
	
	public ArrayList<PostEntry> getAllPost(SQLiteDatabase db, long familyId) {
		String[] ids = findFamily(db, familyId);
		String unit = PostUserId+"=? OR ";
		String result = "";
		for (int i =0; i < ids.length-1; i++) {
			result += unit;
		}
		result+= PostUserId+"=?";
		Cursor c = db.query(PostTable, new String[] {PostUserId, PostDiscription, PostFeeling, PostPic, PostDoodle, PostTime, PostAudio, PostVideo}, result, ids, null,null, PostTime);
	    ArrayList<PostEntry> posts = new ArrayList<PostEntry>();
	     
	    while(c.moveToNext()) {
	    	PostEntry post = new PostEntry(c.getInt(c.getColumnIndex("PostUserId")), c.getInt(c.getColumnIndex("Feeling")), c.getString(c.getColumnIndex("PostDiscription")), c.getString(c.getColumnIndex("PostPic")),c.getString(c.getColumnIndex("PostDoodle")),c.getString(c.getColumnIndex("PostTime")),c.getString(c.getColumnIndex("PostAudio")),c.getString(c.getColumnIndex("PostVideo")));
	    	posts.add(post);
	    }
	    
	    return posts;
	}
	
	public ArrayList<PostEntry> getAllPostFromIndividual(SQLiteDatabase db, long id) {
		Cursor c = db.query(PostTable, new String[] {PostUserId, PostDiscription, PostFeeling, PostPic, PostDoodle, PostTime, PostAudio, PostVideo}, PostUserId+"=?", new String[]{id+""}, null,null,null);
	    ArrayList<PostEntry> posts = new ArrayList<PostEntry>();
	     
	    while(c.moveToNext()) {
	    	PostEntry post = new PostEntry(c.getInt(c.getColumnIndex("PostUserId")), c.getInt(c.getColumnIndex("Feeling")), c.getString(c.getColumnIndex("PostDiscription")), c.getString(c.getColumnIndex("PostPic")),c.getString(c.getColumnIndex("PostDoodle")),c.getString(c.getColumnIndex("PostTime")),c.getString(c.getColumnIndex("PostAudio")),c.getString(c.getColumnIndex("PostVideo")));
	    	posts.add(post);
	    }
	    
	    return posts;
	}
	
	public Family[] getAllFamilies(SQLiteDatabase db) {
		Cursor c = db.rawQuery("select * from Families", null);
		Family[] result = new Family[c.getCount()];
    	
		int index = 0;
		while(c.moveToNext()) {
			result[index] = new Family(c.getInt(c.getColumnIndex("FamilyId")), c.getString(c.getColumnIndex("FamilyName")));
			index++;
		}
		
		return result;
    }
	
}
	
	
