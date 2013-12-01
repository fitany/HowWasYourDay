package edu.berkeley.cs160.howwasyourday.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
	
	public DatabaseHelper(Context context) {
		super(context, dbName, null, 3); 
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		  // TODO Auto-generated method stub
		  
		  db.execSQL("CREATE TABLE "+familyTable+" ("+FamilyId+ " INTEGER PRIMARY KEY , "+FamilyName+ " TEXT UNIQUE)");
		  
		  db.execSQL("CREATE TABLE "+userTable+" ("+UserId+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ UserEmail+" TEXT NOT NULL UNIQUE, "+UserPassword+" TEXT NOT NULL, " + UserName +" TEXT, " +UserType+ " TEXT, " + UserFamilyId + " INTEGER ,FOREIGN KEY ("+UserFamilyId+") REFERENCES "+familyTable+" ("+FamilyId+"));");
		  
		  
//		  db.execSQL("CREATE TRIGGER fk_empdept_deptid " +
//		    " BEFORE INSERT "+
//		    " ON "+employeeTable+
//		    
//		    " FOR EACH ROW BEGIN"+
//		    " SELECT CASE WHEN ((SELECT "+colDeptID+" FROM "+deptTable+" WHERE "+colDeptID+"=new."+colDept+" ) IS NULL)"+" THEN RAISE (ABORT,'Foreign Key Violation') END;"+"  END;");
//		  
//		  db.execSQL("CREATE VIEW "+viewEmps+
//		    " AS SELECT "+employeeTable+"."+colID+" AS _id,"+
//		    " "+employeeTable+"."+colName+","+
//		    " "+employeeTable+"."+colAge+","+
//		    " "+deptTable+"."+colDeptName+""+
//		    " FROM "+employeeTable+" JOIN "+deptTable+
//		    " ON "+employeeTable+"."+colDept+" ="+deptTable+"."+colDeptID
//		    );
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+familyTable);
		db.execSQL("DROP TABLE IF EXISTS "+userTable);
//	  
//	    db.execSQL("DROP TRIGGER IF EXISTS dept_id_trigger");
//	    db.execSQL("DROP TRIGGER IF EXISTS dept_id_trigger22");
//	    db.execSQL("DROP TRIGGER IF EXISTS fk_empdept_deptid");
//	    db.execSQL("DROP VIEW IF EXISTS "+viewEmps);
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
	
}
	
	
