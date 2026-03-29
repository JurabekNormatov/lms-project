package com.lms.test;

import com.lms.exception.LMSException;


import com.lms.manager.CourseManager;
import com.lms.model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CourseManagerTest {

    private CourseManager courseManager;

    @BeforeEach
    void setUp() {
        courseManager = CourseManager.getInstance();
    }

    @Test
    void testKursHinzufuegen() throws LMSException {
        Course course = new Course(
            "Java Grundlagen", "Beschreibung",
            "Programmierung", "ANFAENGER",
            49.99, 0, "", LocalDateTime.now()
        );
        courseManager.hinzufuegen(course);
        assertNotNull(courseManager.findeNachTitel("Java Grundlagen"));
    }

    @Test
    void testKursSuchen() throws LMSException {
        Course course = new Course(
            "Python Kurs", "Python lernen",
            "Programmierung", "ANFAENGER",
            29.99, 0, "", LocalDateTime.now()
        );
        courseManager.hinzufuegen(course);
        List<Course> ergebnis = courseManager.suchen("Python");
        assertFalse(ergebnis.isEmpty());
    }

    @Test
    void testDuplikaterKurs() {
        assertThrows(LMSException.class, () -> {
            Course c1 = new Course("Gleicher Kurs", "",
                "IT", "ANFAENGER", 10.0, 0, "", LocalDateTime.now());
            Course c2 = new Course("Gleicher Kurs", "",
                "IT", "ANFAENGER", 10.0, 0, "", LocalDateTime.now());
            courseManager.hinzufuegen(c1);
            courseManager.hinzufuegen(c2);
        });
    }

    @Test
    void testFilterNachKategorie() throws LMSException {
        Course course = new Course(
            "SQL Kurs", "Datenbanken lernen",
            "Datenbanken", "MITTEL",
            39.99, 0, "", LocalDateTime.now()
        );
        courseManager.hinzufuegen(course);
        List<Course> ergebnis = courseManager.findeNachKategorie("Datenbanken");
        assertFalse(ergebnis.isEmpty());
    }
}