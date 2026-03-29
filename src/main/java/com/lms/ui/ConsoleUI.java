package com.lms.ui;

import com.lms.exception.LMSException;
import com.lms.model.User;
import com.lms.manager.CourseManager;
import com.lms.manager.EnrollmentManager;
import com.lms.manager.UserManager;
import com.lms.manager.BewertungManager;
import com.lms.model.Course;
import com.lms.model.Enrollment;
import com.lms.model.Student;
import com.lms.model.Instructor;
import com.lms.model.Bewertung;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private Scanner scanner = new Scanner(System.in);
    private UserManager userManager = UserManager.getInstance();
    private CourseManager courseManager = CourseManager.getInstance();
    private EnrollmentManager enrollmentManager = EnrollmentManager.getInstance();
    private BewertungManager bewertungManager = BewertungManager.getInstance();
    
    public void starten() {
    	userManager.ausDatenbankLaden();
        courseManager.ausDatenbankLaden();
        System.out.println("=================================");
        System.out.println("  LMS — Lernmanagementsystem");
        System.out.println("=================================");

        boolean laufen = true;
        while (laufen) {
            zeigeHauptmenue();
            int wahl = leseZahl();
            switch (wahl) {
                case 1:
                    benutzerMenue();
                    break;
                case 2:
                    kursMenue();
                    break;
                case 3:
                    einschreibungMenue();
                    break;
                case 4:
                    System.out.println("Auf Wiedersehen!");
                    laufen = false;
                    break;
                default:
                    System.out.println("Ungültige Eingabe!");
            }
        }
    }

    private void zeigeHauptmenue() {
        System.out.println("\n=== Hauptmenü ===");
        System.out.println("1. Benutzer verwalten");
        System.out.println("2. Kurse verwalten");
        System.out.println("3. Einschreibungen verwalten");
        System.out.println("4. Beenden");
        System.out.print("→ Wähle eine Option: ");
    }

    // ═══ BENUTZER MENÜ ═══
    private void benutzerMenue() {
        System.out.println("\n=== Benutzer Menü ===");
        System.out.println("1. Student hinzufügen");
        System.out.println("2. Instructor hinzufügen");
        System.out.println("3. Alle Benutzer anzeigen");
        System.out.println("4. Benutzer suchen");
        System.out.println("5. Zurück");
        System.out.print("→ Wähle eine Option: ");

        int wahl = leseZahl();
        switch (wahl) {
            case 1:
                studentHinzufuegen();
                break;
            case 2:
                instructorHinzufuegen();
                break;
            case 3:
                alleBenutzerAnzeigen();
                break;
            case 4:
                benutzerSuchen();
                break;
            case 5:
                break;
            default:
                System.out.println("Ungültige Eingabe!");
        }
    }

    private void studentHinzufuegen() {
        System.out.println("\n--- Student hinzufügen ---");
        System.out.print("Name: ");
        String name = scanner.next();
        System.out.print("Email: ");
        String email = scanner.next();
        System.out.print("Passwort: ");
        String password = scanner.next();
        System.out.print("Matrikelnummer: ");
        String matrikel = scanner.next();

        try {
            Student student = new Student(name, email, password,
                LocalDateTime.now(), matrikel);
            userManager.hinzufuegen(student);
            System.out.println("✅ Student erfolgreich hinzugefügt!");
        } catch (LMSException e) {
            System.out.println("❌ Fehler: " + e.getMessage());
        }
    }

    private void instructorHinzufuegen() {
        System.out.println("\n--- Instructor hinzufügen ---");
        System.out.print("Name: ");
        String name = scanner.next();
        System.out.print("Email: ");
        String email = scanner.next();
        System.out.print("Passwort: ");
        String password = scanner.next();
        System.out.print("Fachbereich: ");
        String fachbereich = scanner.next();

        try {
            Instructor instructor = new Instructor(name, email, password,
                LocalDateTime.now(), fachbereich);
            userManager.hinzufuegen(instructor);
            System.out.println("✅ Instructor erfolgreich hinzugefügt!");
        } catch (LMSException e) {
            System.out.println("❌ Fehler: " + e.getMessage());
        }
    }

    private void alleBenutzerAnzeigen() {
        System.out.println("\n--- Alle Benutzer ---");
        List<User> users = userManager.alleUser();
        if (users.isEmpty()) {
            System.out.println("Keine Benutzer vorhanden!");
            return;
        }
        for (Object u : users) {
            System.out.println(u);
        }
    }

    private void benutzerSuchen() {
        System.out.print("Email eingeben: ");
        String email = scanner.next();
        try {
            System.out.println("Gefunden: " + userManager.findeNachEmail(email));
        } catch (LMSException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void kursMenue() {
        System.out.println("\n=== Kurs Menü ===");
        System.out.println("1. Kurs hinzufügen");
        System.out.println("2. Alle Kurse anzeigen");
        System.out.println("3. Kurs suchen");
        System.out.println("4. Nach Schwierigkeit filtern");
        System.out.println("5. Nach Preis filtern");
        System.out.println("6. Zurück");
        System.out.print("→ Wähle eine Option: ");

        int wahl = leseZahl();
        switch (wahl) {
            case 1:
                kursHinzufuegen();
                break;
            case 2:
                alleKurseAnzeigen();
                break;
            case 3:
                kursSuchen();
                break;
            case 4:
                kursFilterSchwierigkeit();
                break;
            case 5:
                kursFilterPreis();
                break;
            case 6:
                break;
            default:
                System.out.println("Ungültige Eingabe!");
        }
    }

    private void kursSuchen() {
        System.out.print("Suchbegriff: ");
        scanner.nextLine();
        String begriff = scanner.nextLine();
        List<Course> ergebnis = courseManager.suchen(begriff);
        if (ergebnis.isEmpty()) {
            System.out.println("Keine Kurse gefunden!");
        } else {
            for (Course c : ergebnis) {
                System.out.println("ID: " + c.getId() + " | " + c);
            }
        }
    }

    private void kursFilterSchwierigkeit() {
        System.out.print("Schwierigkeit (ANFAENGER/MITTEL/FORTGESCHRITTEN): ");
        scanner.nextLine();
        String schwierigkeit = scanner.nextLine();
        List<Course> ergebnis = courseManager.filterNachSchwierigkeit(schwierigkeit);
        if (ergebnis.isEmpty()) {
            System.out.println("Keine Kurse gefunden!");
        } else {
            for (Course c : ergebnis) {
                System.out.println("ID: " + c.getId() + " | " + c);
            }
        }
    }

    private void kursFilterPreis() {
        System.out.print("Maximaler Preis: ");
        scanner.nextLine();
        double maxPreis = Double.parseDouble(scanner.nextLine());
        List<Course> ergebnis = courseManager.filterNachPreis(maxPreis);
        if (ergebnis.isEmpty()) {
            System.out.println("Keine Kurse gefunden!");
        } else {
            for (Course c : ergebnis) {
                System.out.println("ID: " + c.getId() + " | " + c +
                    " | Preis: " + c.getPreis() + "€");
            }
        }
    }

    private void kursHinzufuegen() {
        System.out.println("\n--- Kurs hinzufügen ---");
        scanner.nextLine(); // Zeilenumbruch löschen

        System.out.print("Titel: ");
        String titel = scanner.nextLine();

        System.out.print("Kategorie: ");
        String kategorie = scanner.nextLine();

        System.out.print("Schwierigkeitsgrad (ANFAENGER/MITTEL/FORTGESCHRITTEN): ");
        String schwierigkeit = scanner.nextLine();

        System.out.print("Preis: ");
        double preis = Double.parseDouble(scanner.nextLine());

        System.out.print("Instructor ID: ");
        int instructorId = Integer.parseInt(scanner.nextLine());

        try {
            Course course = new Course(titel, "", kategorie,
                schwierigkeit, preis, instructorId, "", LocalDateTime.now());
            courseManager.hinzufuegen(course);
            System.out.println("✅ Kurs erfolgreich hinzugefügt!");
        } catch (LMSException e) {
            System.out.println("❌ Fehler: " + e.getMessage());
        }
    }

    private void alleKurseAnzeigen() {
        System.out.println("\n--- Alle Kurse ---");
        List<Course> kurse = courseManager.alleKurse();
        if (kurse.isEmpty()) {
            System.out.println("Keine Kurse vorhanden!");
            return;
        }
        for (Course c : kurse) {
            System.out.println("ID: " + c.getId() + " | " + c);
        }
    }

    private void einschreibungMenue() {
        System.out.println("\n=== Einschreibung Menü ===");
        System.out.println("1. Student einschreiben");
        System.out.println("2. Alle Einschreibungen anzeigen");
        System.out.println("3. Kurs bewerten");
        System.out.println("4. Kursbewertungen anzeigen");
        System.out.println("5. Zurück");
        System.out.print("→ Wähle eine Option: ");

        int wahl = leseZahl();
        switch (wahl) {
            case 1:
                studentEinschreiben();
                break;
            case 2:
                alleEinschreibungenAnzeigen();
                break;
            case 3:
                kursBewerten();
                break;
            case 4:
                kursBewertungenAnzeigen();
                break;
            case 5:
                break;
            default:
                System.out.println("Ungültige Eingabe!");
        }
    }

    private void kursBewerten() {
        System.out.println("\n--- Kurs bewerten ---");
        System.out.print("Student ID: ");
        int studentId = leseZahl();
        System.out.print("Kurs ID: ");
        int courseId = leseZahl();
        System.out.print("Sterne (1-5): ");
        int sterne = leseZahl();
        System.out.print("Kommentar: ");
        scanner.nextLine();
        String kommentar = scanner.nextLine();

        try {
            Student student = (Student) userManager.findeNachId(studentId);
            Course course = courseManager.findeNachId(courseId);
            Bewertung b = new Bewertung(
                studentId, courseId, sterne,
                kommentar, student.getName(), course.getTitel()
            );
            bewertungManager.bewerten(b);
            System.out.println("✅ Bewertung erfolgreich!");
        } catch (LMSException e) {
            System.out.println("❌ Fehler: " + e.getMessage());
        }
    }

    private void kursBewertungenAnzeigen() {
        System.out.print("Kurs ID: ");
        int courseId = leseZahl();
        List<Bewertung> bewertungen = bewertungManager.bewertungenFuerKurs(courseId);
        double durchschnitt = bewertungManager.durchschnitt(courseId);

        if (bewertungen.isEmpty()) {
            System.out.println("Keine Bewertungen vorhanden!");
            return;
        }
        System.out.println("\n--- Bewertungen ---");
        for (Bewertung b : bewertungen) {
            System.out.println(b);
        }
        System.out.printf("Durchschnitt: %.1f ⭐%n", durchschnitt);
    }

    private void studentEinschreiben() {
        System.out.println("\n--- Student einschreiben ---");
        System.out.print("Student ID: ");
        int studentId = leseZahl();
        System.out.print("Kurs ID: ");
        int courseId = leseZahl();

        try {
            Student student = (Student) userManager.findeNachId(studentId);
            Course course = courseManager.findeNachId(courseId);
            Enrollment enrollment = new Enrollment(
                studentId, courseId,
                student.getName(), course.getTitel(),
                LocalDateTime.now(), 0.0
            );
            enrollmentManager.einschreiben(enrollment);
            System.out.println("✅ Erfolgreich eingeschrieben!");
        } catch (LMSException e) {
            System.out.println("❌ Fehler: " + e.getMessage());
        }
    }

    private void alleEinschreibungenAnzeigen() {
        System.out.println("\n--- Alle Einschreibungen ---");
        List<Enrollment> einschreibungen = enrollmentManager.alleEinschreibungen();
        if (einschreibungen.isEmpty()) {
            System.out.println("Keine Einschreibungen vorhanden!");
            return;
        }
        for (Enrollment e : einschreibungen) {
            System.out.println(e);
        }
    }

    private int leseZahl() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.next();
            return -1;
        }
    }

    /*public static void main(String[] args) {
        new ConsoleUI().starten();
    }*/
}