package com.lms.model;

public class Lesson {
	
	private int id;
	private String titel;
	private String inhalt;
	private int dauer;
	private int courseId;
	private int reihenfolge;
	private boolean abgeschlossen;
	
	
	public Lesson(int id, String titel, String inhalt, int dauer, int courseId, int reihenfolge, boolean abgeschlossen) {
		this.id = id;
		this.titel = titel;
		this.inhalt = inhalt;
		this.dauer = dauer;
		this.courseId = courseId;
		this.reihenfolge = reihenfolge;
		this.abgeschlossen = abgeschlossen;
	}
	
	public Lesson( String titel, String inhalt, int dauer, int courseId, int reihenfolge, boolean abgeschlossen) {
		
		this.titel = titel;
		this.inhalt = inhalt;
		this.dauer = dauer;
		this.courseId = courseId;
		this.reihenfolge = reihenfolge;
		this.abgeschlossen = abgeschlossen;
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitel() {
		return titel;
	}
	
	public String getInhalt() {
		return inhalt;
	}
	
	public int getDauer() {
		return dauer;
	}
	
	public int getCourseId() {
		return courseId;
	}
	
	public int getReihenfolge() {
		return reihenfolge;
	}
	
	public boolean getAbgeschlossen() {
		return abgeschlossen;
	}
	
	
	public void setId(int id) {
		this.id = id ;
	}
	
	public void setTitel(String titel) {
		this.titel = titel;
	}
	
	public void setInhalt(String inhalt) {
		this.inhalt = inhalt;
	}
	
	public void setDauer(int dauer) {
		this.dauer = dauer;
	}
	
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	public void setReihenfolge(int reihenfolge) {
		this.reihenfolge =  reihenfolge;
	}
	
	public void isAbgeschlossen(boolean abgeschlossen) {
		this.abgeschlossen = abgeschlossen;
	}
	
	@Override
	public String toString() {
		return titel + " (" + dauer + " Min.)";
	}

}
