package com.lms.model;
import java.time.LocalDateTime;

public abstract class User {
	
	private int id;
	private String name;
	private String email;
	private String password;
	private LocalDateTime registrierungsDatum;
	
	//Konstruktor ohne ID
	public User(String name, String email, String password, LocalDateTime registrierungsDatum) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.registrierungsDatum = registrierungsDatum;
	}
	
	//Konstruktor mit ID
	public User(int id, String name, String email, String password, LocalDateTime registrierungsDatum) {
	    this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.registrierungsDatum = registrierungsDatum;
	}
		
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public LocalDateTime getRegistrierungsDatum() {
		return registrierungsDatum;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setRegistrierungsDatum(LocalDateTime registrierungsDatum) {
		this.registrierungsDatum = registrierungsDatum;
	}
	
	public abstract String getRole();
	
	
	
	public String toString() {
		return name + " (" + email + ")";
		
	}

}
