package com.lms.db;

import com.lms.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public boolean speichern(Course course) {
        String sql = "INSERT INTO courses " +
                     "(titel, beschreibung, kategorie, schwierigkeitsgrad, " +
                     "preis, instructor_id, erstellungs_datum) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, course.getTitel());
            stmt.setString(2, course.getBeschreibung());
            stmt.setString(3, course.getKategorie());
            stmt.setString(4, course.getSchwierigkeitsgrad());
            stmt.setDouble(5, course.getPreis());
            stmt.setInt(6, course.getInstructorId());
            stmt.setTimestamp(7, Timestamp.valueOf(course.getErstellungsDatum()));
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Fehler: " + e.getMessage());
            return false;
        }
    }

    public List<Course> alleKurse() {
        List<Course> liste = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Course c = new Course(
                    rs.getInt("id"),
                    rs.getString("titel"),
                    rs.getString("beschreibung"),
                    rs.getString("kategorie"),
                    rs.getString("schwierigkeitsgrad"),
                    rs.getDouble("preis"),
                    rs.getInt("instructor_id"),
                    "",
                    rs.getTimestamp("erstellungs_datum").toLocalDateTime()
                );
                liste.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
        return liste;
    }

    public boolean loeschen(int id) {
        String sql = "DELETE FROM courses WHERE id=?";
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