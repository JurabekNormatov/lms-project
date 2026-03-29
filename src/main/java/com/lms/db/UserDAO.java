package com.lms.db;

import com.lms.model.Instructor;
import com.lms.model.Student;
import com.lms.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public boolean speichern(User user) {
        String sql = "INSERT INTO users " +
                     "(name, email, password, role, extra_info, registrierungs_datum) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            String extraInfo = "";
            if (user instanceof Student) {
                extraInfo = ((Student) user).getMatrikelnummer();
            } else if (user instanceof Instructor) {
                extraInfo = ((Instructor) user).getFachbereich();
            }

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.setString(5, extraInfo);
            stmt.setTimestamp(6, Timestamp.valueOf(user.getRegistrierungsDatum()));
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Fehler: " + e.getMessage());
            return false;
        }
    }

    public List<User> alleUser() {
        List<User> liste = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String role = rs.getString("role");
                if (role.equals("STUDENT")) {
                    Student s = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getTimestamp("registrierungs_datum").toLocalDateTime(),
                        rs.getString("extra_info")
                    );
                    liste.add(s);
                } else if (role.equals("INSTRUCTOR")) {
                    Instructor i = new Instructor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getTimestamp("registrierungs_datum").toLocalDateTime(),
                        rs.getString("extra_info")
                    );
                    liste.add(i);
                }
            }
        } catch (SQLException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
        return liste;
    }

    public boolean loeschen(int id) {
        String sql = "DELETE FROM users WHERE id=?";
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