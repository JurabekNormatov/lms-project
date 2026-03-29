package com.lms.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.lms.model.Enrollment;

public class EnrollmentDAO {
	
	public boolean speichern(Enrollment enrollment) {
        String sql = "INSERT INTO enrollments\n"
        		+ "       (student_id, course_id, einschreibe_datum, fortschritt)\n"
        		+ "       VALUES (?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, enrollment.getStudentId());
            stmt.setInt(2, enrollment.getCourseId());
            stmt.setTimestamp(3, Timestamp.valueOf(enrollment.getEinschreibeDatum()));
            stmt.setDouble(4, enrollment.getFortschritt());
        
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Fehler: " + e.getMessage());
            return false;
        }
    }
	
	public List<Enrollment> alleEnrollments() {
        List<Enrollment> liste = new ArrayList<>();
        String sql = "SELECT * FROM enrollments";
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Enrollment e = new Enrollment(
                    rs.getInt("id"),
                    rs.getInt("student_id"),
                    rs.getInt("course_id"),
                    "", // studentName
                    "", // courseTitel
                    rs.getTimestamp("einschreibe_datum").toLocalDateTime(),
                    rs.getDouble("fortschritt")
                );
                liste.add(e);
            }
        } catch (SQLException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
        return liste;
    }
	
	public boolean loeschen(int id) {
        String sql = "DELETE FROM enrollments WHERE id=?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Fehler: " + e.getMessage());
            return false;
        }
    }

}
