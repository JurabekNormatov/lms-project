package com.lms.ui;

import com.lms.exception.LMSException;
import com.lms.manager.CourseManager;
import com.lms.manager.EnrollmentManager;
import com.lms.manager.UserManager;
import com.lms.model.Course;
import com.lms.model.Enrollment;
import com.lms.model.Student;
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
import java.time.LocalDateTime;
import java.util.List;

public class EinschreibungenView {

    private EnrollmentManager enrollmentManager = EnrollmentManager.getInstance();
    private UserManager userManager = UserManager.getInstance();
    private CourseManager courseManager = CourseManager.getInstance();

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Einschreibungen");

        // Tabelle
        TableView<Enrollment> tabelle = new TableView<>();

        TableColumn<Enrollment, String> studentCol = new TableColumn<>("Student");
        studentCol.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getStudentName()));

        TableColumn<Enrollment, String> kursCol = new TableColumn<>("Kurs");
        kursCol.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getCourseTitel()));

        TableColumn<Enrollment, String> fortschrittCol = new TableColumn<>("Fortschritt");
        fortschrittCol.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getFortschritt() + "%"));

        TableColumn<Enrollment, String> datumCol = new TableColumn<>("Datum");
        datumCol.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getEinschreibeDatum()
                .toLocalDate().toString()));

        tabelle.getColumns().addAll(studentCol, kursCol, fortschrittCol, datumCol);
        tabelle.getItems().addAll(enrollmentManager.alleEinschreibungen());

        // Formular — Student auswählen
        ComboBox<User> studentBox = new ComboBox<>();
        studentBox.getItems().addAll(userManager.alleUser());
        studentBox.setPromptText("Student auswählen");

        // Formular — Kurs auswählen
        ComboBox<Course> kursBox = new ComboBox<>();
        kursBox.getItems().addAll(courseManager.alleKurse());
        kursBox.setPromptText("Kurs auswählen");

        Label fehlerLabel = new Label("");
        fehlerLabel.setTextFill(Color.web("#f38ba8"));

        // Einschreiben Button
        Button einschreibenBtn = new Button("➕ Einschreiben");
        einschreibenBtn.setStyle("-fx-background-color: #cba6f7; -fx-font-weight: bold;");
        einschreibenBtn.setOnAction(e -> {
            try {
                User selectedUser = studentBox.getValue();
                Course selectedKurs = kursBox.getValue();

                if (selectedUser == null || selectedKurs == null) {
                    fehlerLabel.setText("❌ Bitte Student und Kurs auswählen!");
                    return;
                }

                Enrollment enrollment = new Enrollment(
                    selectedUser.getId(),
                    selectedKurs.getId(),
                    selectedUser.getName(),
                    selectedKurs.getTitel(),
                    LocalDateTime.now(),
                    0.0
                );
                enrollmentManager.einschreiben(enrollment);
                tabelle.getItems().clear();
                tabelle.getItems().addAll(enrollmentManager.alleEinschreibungen());
                fehlerLabel.setText("");
            } catch (LMSException ex) {
                fehlerLabel.setText("❌ " + ex.getMessage());
            }
        });

        // Ausschreiben Button
        Button ausschreibenBtn = new Button("🗑️ Ausschreiben");
        ausschreibenBtn.setStyle("-fx-background-color: #f38ba8; -fx-font-weight: bold;");
        ausschreibenBtn.setOnAction(e -> {
            Enrollment selected = tabelle.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    enrollmentManager.ausschreiben(selected.getId());
                    tabelle.getItems().clear();
                    tabelle.getItems().addAll(enrollmentManager.alleEinschreibungen());
                } catch (LMSException ex) {
                    fehlerLabel.setText("❌ " + ex.getMessage());
                }
            }
        });

        HBox buttonBox = new HBox(10, einschreibenBtn, ausschreibenBtn);

        Label studentLabel = new Label("Student:");
        studentLabel.setTextFill(Color.web("#cdd6f4"));
        Label kursLabel = new Label("Kurs:");
        kursLabel.setTextFill(Color.web("#cdd6f4"));

        VBox formular = new VBox(8,
            studentLabel, studentBox,
            kursLabel, kursBox,
            fehlerLabel, buttonBox
        );

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #1e1e2e;");

        Label titel = new Label("📝 Einschreibungen");
        titel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titel.setTextFill(Color.web("#cba6f7"));

        layout.getChildren().addAll(titel, formular, tabelle);

        Scene scene = new Scene(layout, 650, 600);
        stage.setScene(scene);
        stage.show();
    }
}