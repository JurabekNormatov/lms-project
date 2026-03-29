package com.lms.test;

import com.lms.exception.LMSException;


import com.lms.manager.UserManager;
import com.lms.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {

     private UserManager userManager;

    @BeforeEach
    void setUp() {
        // Neue Instanz für jeden Test
        userManager = UserManager.getInstance();
    }

    @Test
    void testStudentHinzufuegen() throws LMSException {
        Student student = new Student(
            "Test Student",
            "test@test.de",
            "password123",
            LocalDateTime.now(),
            "12345"
        );
        userManager.hinzufuegen(student);
        assertNotNull(userManager.findeNachEmail("test@test.de"));
    }

    @Test
    void testDuplikateEmail() {
        assertThrows(LMSException.class, () -> {
            Student s1 = new Student("Student1", "gleich@test.de",
                "pass", LocalDateTime.now(), "111");
            Student s2 = new Student("Student2", "gleich@test.de",
                "pass", LocalDateTime.now(), "222");
            userManager.hinzufuegen(s1);
            userManager.hinzufuegen(s2);
        });
    }

    @Test
    void testUserNichtGefunden() {
        assertThrows(LMSException.class, () -> {
            userManager.findeNachEmail("nichtvorhanden@test.de");
        });
    }

    @Test
    void testAlleUser() throws LMSException {
        int vorher = userManager.alleUser().size();
        Student student = new Student(
            "Neuer Student",
            "neu@test.de",
            "pass",
            LocalDateTime.now(),
            "99999"
        );
        userManager.hinzufuegen(student);
        assertTrue(userManager.alleUser().size() > vorher);
    }
}