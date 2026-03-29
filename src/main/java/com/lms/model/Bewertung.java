package com.lms.model;

public class Bewertung {
	
	private int id;
	private int studentId;
	private int courseId;
	private int sterne;
	private String kommentar;
	private String studentName;
	private String courseTitel;
	
	public Bewertung(int id, int studentId, int courseId, int sterne, String kommentar, String studentName, String courseTitel) {
		this.id = id;
		this.studentId = studentId;
		this.courseId = courseId;
		this.sterne = sterne;
		this.kommentar = kommentar;
		this.studentName = studentName;
		this.courseTitel = courseTitel;
	}
	
	public Bewertung( int studentId, int courseId, int sterne, String kommentar, String studentName, String courseTitel) {
		
		this.studentId = studentId;
		this.courseId = courseId;
		this.sterne = sterne;
		this.kommentar = kommentar;
		this.studentName = studentName;
		this.courseTitel = courseTitel;
	}
	
	public int getId() {
		return id;
	}
	
	public int getStudentId() {
		return studentId;
	}
	
	public int getCourseId() {
		return courseId;
	}
	
	public int getSterne() {
		return sterne;
	}
	
	public String getKommentar() {
		return kommentar;
	}
	
	public String getStudentName() {
		return studentName;
	}
	
	public String getCourseTitel() {
		return courseTitel;
	}
	
	public void setId(int id) {
		this.id = id ;
	}
	
	public void setStudentId(int studentId) {
		this.studentId = studentId ;
	}
	
	public void setCourseId(int courseId) {
		this.courseId = courseId ;
	}
	
	public void setSterne(int sterne) {
		this.sterne = sterne ;
	}
	
	public void setKommentar(String kommentar) {
		this.kommentar = kommentar ;
	}
	
	public void setStudentName(String studentName) {
		this.studentName = studentName ;
	}
	
	public void setCourseTitel(String courseTitel) {
		this.courseTitel = courseTitel ;
	}
	
	@Override
	public String toString() {
		return studentName + ": " + sterne + "⭐ - " + kommentar;
	}

}
