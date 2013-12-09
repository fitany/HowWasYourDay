package edu.berkeley.cs160.howwasyourday;

public class User {
	public long id;
	public String firstname;
	public String lastname;
	public String type;
	public long familyId;
	
	public User(long id, String firstname, String lastname, String userType) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.type = userType;
		this.familyId = 1;
	}
	
}
