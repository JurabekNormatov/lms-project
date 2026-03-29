package com.lms.util;

import com.lms.manager.CourseManager;
import com.lms.manager.EnrollmentManager;
import com.lms.manager.UserManager;
import com.lms.model.Course;
import com.lms.model.Enrollment;
import com.lms.model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class BackupManager {

    private static BackupManager instance;
    private LMSLogger logger = LMSLogger.getInstance();

    private BackupManager() {}

    public static BackupManager getInstance() {
        if (instance == null) {
            instance = new BackupManager();
        }
        return instance;
    }

    public void exportiereJSON(String dateipfad) {
        try {
            StringBuilder json = new StringBuilder();
            json.append("{\n");

            // Users exportieren
            json.append("  \"users\": [\n");
            List<User> users = UserManager.getInstance().alleUser();
            for (int i = 0; i < users.size(); i++) {
                User u = users.get(i);
                json.append("    {\n");
                json.append("      \"id\": ").append(u.getId()).append(",\n");
                json.append("      \"name\": \"").append(u.getName()).append("\",\n");
                json.append("      \"email\": \"").append(u.getEmail()).append("\",\n");
                json.append("      \"role\": \"").append(u.getRole()).append("\"\n");
                json.append("    }");
                if (i < users.size() - 1) json.append(",");
                json.append("\n");
            }
            json.append("  ],\n");

            // Kurse exportieren
            json.append("  \"courses\": [\n");
            List<Course> kurse = CourseManager.getInstance().alleKurse();
            for (int i = 0; i < kurse.size(); i++) {
                Course c = kurse.get(i);
                json.append("    {\n");
                json.append("      \"id\": ").append(c.getId()).append(",\n");
                json.append("      \"titel\": \"").append(c.getTitel()).append("\",\n");
                json.append("      \"kategorie\": \"").append(c.getKategorie()).append("\",\n");
                json.append("      \"preis\": ").append(c.getPreis()).append("\n");
                json.append("    }");
                if (i < kurse.size() - 1) json.append(",");
                json.append("\n");
            }
            json.append("  ],\n");

            // Einschreibungen exportieren
            json.append("  \"enrollments\": [\n");
            List<Enrollment> einschreibungen = EnrollmentManager.getInstance().alleEinschreibungen();
            for (int i = 0; i < einschreibungen.size(); i++) {
                Enrollment e = einschreibungen.get(i);
                json.append("    {\n");
                json.append("      \"id\": ").append(e.getId()).append(",\n");
                json.append("      \"studentName\": \"").append(e.getStudentName()).append("\",\n");
                json.append("      \"courseTitel\": \"").append(e.getCourseTitel()).append("\"\n");
                json.append("    }");
                if (i < einschreibungen.size() - 1) json.append(",");
                json.append("\n");
            }
            json.append("  ],\n");

            json.append("  \"exportDatum\": \"").append(LocalDateTime.now()).append("\"\n");
            json.append("}");

            FileWriter writer = new FileWriter(dateipfad);
            writer.write(json.toString());
            writer.close();

            logger.info("Backup erfolgreich: " + dateipfad);
            System.out.println("✅ Backup gespeichert: " + dateipfad);

        } catch (IOException e) {
            logger.warning("Backup Fehler: " + e.getMessage());
            System.out.println("❌ Backup Fehler: " + e.getMessage());
        }
    }
}