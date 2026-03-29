package com.lms.model;
import java.time.LocalDateTime;

public class Enrollment {
	
	private int id;
	private int studentId;
	private int courseId;
	private String studentName;
	private String courseTitel;
	private LocalDateTime einschreibeDatum;
	private double fortschritt;
	
	public Enrollment(int id, int studentId, int courseId, String studentName, String courseTitel, LocalDateTime einschreibeDatum, double fortschritt) {
		this.id = id;
		this.studentId = studentId;
		this.courseId = courseId;
		this.studentName = studentName;
		this.courseTitel = courseTitel;
		this.einschreibeDatum = einschreibeDatum;
		this.fortschritt = fortschritt;
	}
	
	public Enrollment( int studentId, int courseId, String studentName, String courseTitel, LocalDateTime einschreibeDatum, double fortschritt) {
		
		this.studentId = studentId;
		this.courseId = courseId;
		this.studentName = studentName;
		this.courseTitel = courseTitel;
		this.einschreibeDatum = einschreibeDatum;
		this.fortschritt = fortschritt;
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
	
	public String getStudentName() {
		return studentName;
	}
	
	public String getCourseTitel() {
		return courseTitel;
	}
	
	public LocalDateTime getEinschreibeDatum() {
		return einschreibeDatum;
	}
	
	public double getFortschritt() {
		return fortschritt;
	}
	
	public void setId(int id) {
		this.id = id ;
	}
	
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	public void setCourseId(int courseId) {
		this.courseId = courseId ;
	}
	
	public void setStudentName(String studentName) {
		this.studentName = studentName ;
	}
	
	public void getCourseTitel(String courseTitel) {
		this.courseTitel = courseTitel ;
	}
	
	public void setEinschreibeDatum(LocalDateTime einschreibeDatum) {
		this.einschreibeDatum = einschreibeDatum ;
	}
	
	public void setFortschritt(double fortschritt) {
		this.fortschritt = fortschritt ;
	}
	
	@Override
	public String toString() {
		return studentName + " → " + courseTitel;
	}


}
