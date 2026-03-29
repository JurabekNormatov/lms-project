package com.lms.model;

import java.time.LocalDateTime;

public class Instructor extends User{
	
	private String fachbereich;
	
	public Instructor(int id, String name, String email, String password, LocalDateTime registrierungsDatum, String fachbereich) {
		super( id,  name,  email,  password,  registrierungsDatum);
		
		this.fachbereich = fachbereich;
	}
	
	public Instructor( String name, String email, String password, LocalDateTime registrierungsDatum, String fachbereich) {
		super(   name,  email,  password,  registrierungsDatum);
		this.fachbereich = fachbereich;
	}
	
	
	public String getFachbereich() {
		return fachbereich;
	}
	
	
	public void setFachbereich(String matrikelnummer) {
		this.fachbereich = matrikelnummer;
	}
	
	
	@Override
	public String getRole() {
	    return "INSTRUCTOR";
	}
	
	@Override
	public String toString() {
		return getName()+ " (" + fachbereich + ")";
		
	}

}
