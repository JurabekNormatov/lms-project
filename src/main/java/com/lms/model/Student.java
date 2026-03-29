package com.lms.model;

import java.time.LocalDateTime;

public class Student extends User{
	
	private String matrikelnummer;
	
	
	public Student(int id, String name, String email, String password, LocalDateTime registrierungsDatum, String matrikelnummer) {
		super( id,  name,  email,  password,  registrierungsDatum);
		
		this.matrikelnummer = matrikelnummer;
	}
	
	public Student( String name, String email, String password, LocalDateTime registrierungsDatum, String matrikelnummer) {
		super(   name,  email,  password,  registrierungsDatum);
		this.matrikelnummer = matrikelnummer;
	}
	
	
	public String getMatrikelnummer() {
		return matrikelnummer;
	}
	
	
	public void setMatrikelnummer(String matrikelnummer) {
		this.matrikelnummer = matrikelnummer;
	}
	
	
	@Override
	public String getRole() {
	    return "STUDENT";
	}
	
	@Override
	public String toString() {
		return getName()+ " (" + matrikelnummer + ")";
		
	}

}
