package com.lms.model;
import java.time.LocalDateTime;

public class Course {
	
	private int id;
	private String titel;
	private String beschreibung;
	private String kategorie;
	private String schwierigkeitsgrad;
	private double preis;
	private int instructorId;
	private String instructorName;
	private LocalDateTime erstellungsDatum;
	
	
	public Course(int id, String titel, String beschreibung, String kategorie,String schwierigkeitsgrad, double preis, int instructorId, String instructorName, LocalDateTime erstellungsDatum) {
		this.id = id;
		this.titel = titel;
		this.beschreibung = beschreibung;
		this.kategorie = kategorie;
		this.schwierigkeitsgrad = schwierigkeitsgrad;
		this.preis = preis;
		this.instructorId = instructorId;
		this.instructorName = instructorName;
		this.erstellungsDatum = erstellungsDatum;
	}
	
	public Course( String titel, String beschreibung, String kategorie,String schwierigkeitsgrad, double preis, int instructorId, String instructorName, LocalDateTime erstellungsDatum) {
		
		this.titel = titel;
		this.beschreibung = beschreibung;
		this.kategorie = kategorie;
		this.schwierigkeitsgrad = schwierigkeitsgrad;
		this.preis = preis;
		this.instructorId = instructorId;
		this.instructorName = instructorName;
		this.erstellungsDatum = erstellungsDatum;
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitel() {
		return titel;
	}
	
	public String getBeschreibung() {
		return beschreibung;
	}
	
	public String getKategorie() {
		return kategorie;
	}
	
	public String getSchwierigkeitsgrad() {
		return schwierigkeitsgrad;
	}
	
	public double getPreis() {
		return preis;
	}
	
	public int getInstructorId() {
		return instructorId;
	}
	
	public String getInstructorName() {
		return instructorName;
	}
	
	public LocalDateTime getErstellungsDatum() {
		return erstellungsDatum;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setTitel(String titel) {
		this.titel = titel;
	}
	
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	
	public void setKategorie(String kategorie) {
		this.kategorie = kategorie ;
	}
	
	public void setSchwierigkeitsgrad(String schwierigkeitsgrad) {
		this.schwierigkeitsgrad = schwierigkeitsgrad;
	}
	
	public void setPreis(double preis) {
		this.preis = preis;
	}
	
	public void setInstructorId(int instructorId) {
		this.instructorId = instructorId;
	}
	
	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName ;
	}
	
	public void setErstellungsDatum(LocalDateTime erstellungsDatum) {
		this.erstellungsDatum = erstellungsDatum ;
	}
	
	@Override
	public String toString(){
		return titel + " (" + kategorie + ")";
	}

}
