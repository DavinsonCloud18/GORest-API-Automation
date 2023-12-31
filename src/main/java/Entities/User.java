package Entities;

public class User {
	private int id;
	private String name;
	private String email;
	private String status;
	private String gender;
	
	public User(String name, String email, String status, String gender) {
		this.name = name;
		this.email = email;
		this.status = status;
		this.gender = gender;
	}
	
	public User(int id,String name, String email, String status, String gender) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.status = status;
		this.gender = gender;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String[] checkEmptyAttributes(User user) {
		String message = "";
		if(user.name == null || user.name == "") 
			message = message + "name ";
		if(user.email == null || user.email == "") 
			message = message + "email ";
		if(user.status == null || user.status == "") 
			message = message + "status ";
		if(user.gender == null || user.gender == "") 
			message = message + "gender ";
		
		String[] emptyAttributes = message.split(" ");
		return emptyAttributes;
	}
	
}
