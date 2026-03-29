package com.lms.ui;

import com.lms.exception.LMSException;
import com.lms.manager.BewertungManager;
import com.lms.manager.CourseManager;
import com.lms.manager.UserManager;
import com.lms.model.Bewertung;
import com.lms.model.Course;
import com.lms.model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class BewertungenView {

    private BewertungManager bewertungManager = BewertungManager.getInstance();
    private UserManager userManager = UserManager.getInstance();
    private CourseManager courseManager = CourseManager.getInstance();

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Bewertungen");

        // Tabelle
        TableView<Bewertung> tabelle = new TableView<>();

        TableColumn<Bewertung, String> studentCol = new TableColumn<>("Student");
        studentCol.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getStudentName()));

        TableColumn<Bewertung, String> kursCol = new TableColumn<>("Kurs");
        kursCol.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getCourseTitel()));

        TableColumn<Bewertung, String> sterneCol = new TableColumn<>("Bewertung");
        sterneCol.setCellValueFactory(d ->
            new SimpleStringProperty("⭐".repeat(d.getValue().getSterne())));

        TableColumn<Bewertung, String> kommentarCol = new TableColumn<>("Kommentar");
        kommentarCol.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getKommentar()));

        tabelle.getColumns().addAll(studentCol, kursCol, sterneCol, kommentarCol);
        tabelle.getItems().addAll(bewertungManager.bewertungenFuerKurs(0));

        // Formular
        ComboBox<User> studentBox = new ComboBox<>();
        studentBox.getItems().addAll(userManager.alleUser());
        studentBox.setPromptText("Student auswählen");

        ComboBox<Course> kursBox = new ComboBox<>();
        kursBox.getItems().addAll(courseManager.alleKurse());
        kursBox.setPromptText("Kurs auswählen");

        ComboBox<Integer> sterneBox = new ComboBox<>();
        sterneBox.getItems().addAll(1, 2, 3, 4, 5);
        sterneBox.setValue(5);

        TextField kommentarField = new TextField();
        kommentarField.setPromptText("Kommentar");

        // Durchschnitt anzeigen
        Label durchschnittLabel = new Label("");
        durchschnittLabel.setTextFill(Color.web("#fab387"));
        durchschnittLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        kursBox.setOnAction(e -> {
            if (kursBox.getValue() != null) {
                double avg = bewertungManager.durchschnitt(kursBox.getValue().getId());
                tabelle.getItems().clear();
                tabelle.getItems().addAll(
                    bewertungManager.bewertungenFuerKurs(kursBox.getValue().getId()));
                if (avg > 0) {
                    durchschnittLabel.setText(
                        "⭐ Durchschnitt: " + String.format("%.1f", avg) + "/5");
                } else {
                    durchschnittLabel.setText("Noch keine Bewertungen");
                }
            }
        });

        Label fehlerLabel = new Label("");
        fehlerLabel.setTextFill(Color.web("#f38ba8"));

        Button bewertenBtn = new Button("⭐ Bewerten");
        bewertenBtn.setStyle("-fx-background-color: #fab387; -fx-font-weight: bold;");
        bewertenBtn.setOnAction(e -> {
            try {
                User selectedUser = studentBox.getValue();
                Course selectedKurs = kursBox.getValue();

                if (selectedUser == null || selectedKurs == null) {
                    fehlerLabel.setText("❌ Bitte Student und Kurs auswählen!");
                    return;
                }

                Bewertung b = new Bewertung(
                    selectedUser.getId(),
                    selectedKurs.getId(),
                    sterneBox.getValue(),
                    kommentarField.getText(),
                    selectedUser.getName(),
                    selectedKurs.getTitel()
                );
                bewertungManager.bewerten(b);
                tabelle.getItems().clear();
                tabelle.getItems().addAll(
                    bewertungManager.bewertungenFuerKurs(selectedKurs.getId()));
                double avg = bewertungManager.durchschnitt(selectedKurs.getId());
                durchschnittLabel.setText(
                    "⭐ Durchschnitt: " + String.format("%.1f", avg) + "/5");
                kommentarField.clear();
                fehlerLabel.setText("");
            } catch (LMSException ex) {
                fehlerLabel.setText("❌ " + ex.getMessage());
            }
        });

        Label studentLabel = new Label("Student:");
        studentLabel.setTextFill(Color.web("#cdd6f4"));
        Label kursLabel = new Label("Kurs:");
        kursLabel.setTextFill(Color.web("#cdd6f4"));
        Label sterneLabel = new Label("Sterne:");
        sterneLabel.setTextFill(Color.web("#cdd6f4"));

        VBox formular = new VBox(8,
            studentLabel, studentBox,
            kursLabel, kursBox,
            sterneLabel, sterneBox,
            kommentarField,
            fehlerLabel, bewertenBtn,
            durchschnittLabel
        );

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #1e1e2e;");

        Label titel = new Label("⭐ Bewertungen");
        titel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titel.setTextFill(Color.web("#fab387"));

        layout.getChildren().addAll(titel, formular, tabelle);

        Scene scene = new Scene(layout, 700, 650);
        stage.setScene(scene);
        stage.show();
    }
}