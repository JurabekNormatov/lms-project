package com.lms.manager;

import com.lms.exception.LMSException;

import com.lms.util.LMSLogger;
import com.lms.model.Course;
import com.lms.db.CourseDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseManager {

    // Singleton
    private static CourseManager instance;

    // HashMap — Key=id, Value=Course
    private HashMap<Integer, Course> courses = new HashMap<>();
    private int nextId = 1;

    private CourseManager() {}
    
    private CourseDAO courseDAO = new CourseDAO();
    
    private LMSLogger logger = LMSLogger.getInstance();

    public static CourseManager getInstance() {
        if (instance == null) {
            instance = new CourseManager();
        }
        return instance;
    }

    // Kurs hinzufügen
    public void hinzufuegen(Course course) throws LMSException {
        for (Course c : courses.values()) {
            if (c.getTitel().equals(course.getTitel())) {
                throw new LMSException("Kurs bereits vorhanden: " + course.getTitel());
            }
        }
        courseDAO.speichern(course);
        course.setId(nextId);
        courses.put(nextId, course);
        nextId++;
        logger.info("Kurs hinzugefügt: " + course.getTitel());
    }
    
    public void ausDatenbankLaden() {
        List<Course> dbCourses = courseDAO.alleKurse();
        for (Course c : dbCourses) {
            courses.put(c.getId(), c);
            if (c.getId() >= nextId) {
                nextId = c.getId() + 1;
            }
        }
        logger.info(dbCourses.size() + " Kurse aus DB geladen!");
    }

    // Kurs entfernen
    public void entfernen(int id) throws LMSException {
        if (!courses.containsKey(id)) {
            throw new LMSException("Kurs nicht gefunden: ID " + id);
        }
        courses.remove(id);
        logger.info("Kurs entfernt: ID " + id);
        
    }

    // Kurs nach ID suchen
    public Course findeNachId(int id) throws LMSException {
        Course course = courses.get(id);
        if (course == null) {
            throw new LMSException("Kurs nicht gefunden: ID " + id);
        }
        return course;
    }

    // Kurs nach Titel suchen
    public Course findeNachTitel(String titel) throws LMSException {
        for (Course c : courses.values()) {
            if (c.getTitel().equals(titel)) {
                return c;
            }
        }
        throw new LMSException("Kurs nicht gefunden: " + titel);
    }

    // Alle Kurse
    public List<Course> alleKurse() {
        return new ArrayList<>(courses.values());
    }

    // Kurse nach Kategorie
    public List<Course> findeNachKategorie(String kategorie) {
        List<Course> ergebnis = new ArrayList<>();
        for (Course c : courses.values()) {
            if (c.getKategorie().equals(kategorie)) {
                ergebnis.add(c);
            }
        }
        return ergebnis;
    }
    
    // Suche nach Titel oder Beschreibung
    public List<Course> suchen(String suchbegriff) {
        List<Course> ergebnis = new ArrayList<>();
        String regex = ".*" + suchbegriff + ".*";
        for (Course c : courses.values()) {
            if (c.getTitel().matches(regex) ||
                c.getBeschreibung().matches(regex)) {
                ergebnis.add(c);
            }
        }
        return ergebnis;
    }

    // Filter nach Schwierigkeitsgrad
    public List<Course> filterNachSchwierigkeit(String schwierigkeit) {
        List<Course> ergebnis = new ArrayList<>();
        for (Course c : courses.values()) {
            if (c.getSchwierigkeitsgrad().equals(schwierigkeit)) {
                ergebnis.add(c);
            }
        }
        return ergebnis;
    }

    // Filter nach Preis
    public List<Course> filterNachPreis(double maxPreis) {
        List<Course> ergebnis = new ArrayList<>();
        for (Course c : courses.values()) {
            if (c.getPreis() <= maxPreis) {
                ergebnis.add(c);
            }
        }
        return ergebnis;
    }
}