package com.lms.manager;

import com.lms.util.LMSLogger;
import com.lms.exception.LMSException;
import com.lms.model.Enrollment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EnrollmentManager {

    private static EnrollmentManager instance;
    private HashMap<Integer, Enrollment> enrollments = new HashMap<>();
    private int nextId = 1;

    private EnrollmentManager() {}
    
    private LMSLogger logger = LMSLogger.getInstance();

    public static EnrollmentManager getInstance() {
        if (instance == null) {
            instance = new EnrollmentManager();
        }
        return instance;
    }

    // Student einschreiben
    public void einschreiben(Enrollment enrollment) throws LMSException {
        for (Enrollment e : enrollments.values()) {
            if (e.getStudentId() == enrollment.getStudentId() &&
                e.getCourseId() == enrollment.getCourseId()) {
                throw new LMSException("Bereits eingeschrieben!");
            }
        }
        enrollment.setId(nextId);
        enrollments.put(nextId, enrollment);
        nextId++;
        logger.info("Student einschreiben: " + enrollment.getStudentName());

    }

    // Student ausschreiben
    public void ausschreiben(int id) throws LMSException {
        if (!enrollments.containsKey(id)) {
            throw new LMSException("Einschreibung nicht gefunden: ID " + id);
        }
        enrollments.remove(id);
        logger.info("Student ausgeschrieben: ID " + id); 
    }

    // Alle Einschreibungen eines Kurses
    public List<Enrollment> findeNachKurs(int courseId) {
        List<Enrollment> ergebnis = new ArrayList<>();
        for (Enrollment e : enrollments.values()) {
            if (e.getCourseId() == courseId) {
                ergebnis.add(e);
            }
        }
        return ergebnis;
    }

    // Alle Einschreibungen eines Studenten
    public List<Enrollment> findeNachStudent(int studentId) {
        List<Enrollment> ergebnis = new ArrayList<>();
        for (Enrollment e : enrollments.values()) {
            if (e.getStudentId() == studentId) {
                ergebnis.add(e);
            }
        }
        return ergebnis;
    }

    // Alle Einschreibungen
    public List<Enrollment> alleEinschreibungen() {
        return new ArrayList<>(enrollments.values());
    }
}