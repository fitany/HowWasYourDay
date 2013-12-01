package edu.berkeley.cs160.howwasyourday;

public class User {
	private long id;
	private String name;
	private String type;
	private long familyId = 1;
	
	public User(long id, String name, String userType) {
		this.id = id;
		this.name = name;
		this.type = userType;
	}
}
