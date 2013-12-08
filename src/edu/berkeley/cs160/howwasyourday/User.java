package edu.berkeley.cs160.howwasyourday;

public class User {
	public long id;
	public String name;
	public String type;
	public long familyId = 1;
	
	public User(long id, String name, String userType) {
		this.id = id;
		this.name = name;
		this.type = userType;
	}
}
