package edu.berkeley.cs160.howwasyourday;

public class User {
<<<<<<< HEAD
	public long id;
	public String name;
	public String type;
	public long familyId = 1;
=======
	private long id;
	private String firstname;
	public String lastname;
	private String type;
	private long familyId;
>>>>>>> 7f8094c8ddc0c4bcfdcede00aa6e95d9060fa57e
	
	public User(long id, String firstname, String lastname, String userType) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.type = userType;
		this.familyId = 1;
	}
}
