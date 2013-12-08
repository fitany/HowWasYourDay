package edu.berkeley.cs160.howwasyourday;

public class User {
	private long id;
	private String firstname;
	public String lastname;
	private String type;
	private long familyId = 1;
	
	public User(long id, String firstname, String lastname, String userType) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.type = userType;
	}
}
