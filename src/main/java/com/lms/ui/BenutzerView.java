package com.lms.ui;

import com.lms.exception.LMSException;
import com.lms.manager.UserManager;
import com.lms.model.Instructor;
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

public class BenutzerView {

    private UserManager userManager = UserManager.getInstance();

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Benutzer Verwaltung");

        // Tabelle
        TableView<User> tabelle = new TableView<>();

        TableColumn<User, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getName()));

        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getEmail()));

        TableColumn<User, String> roleCol = new TableColumn<>("Rolle");
        roleCol.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getRole()));

        tabelle.getColumns().addAll(nameCol, emailCol, roleCol);
        tabelle.getItems().addAll(userManager.alleUser());

        // Formular
        TextField nameField     = new TextField();
        TextField emailField    = new TextField();
        PasswordField passField = new PasswordField();
        TextField extraField    = new TextField();

        nameField.setPromptText("Name");
        emailField.setPromptText("Email");
        passField.setPromptText("Passwort");
        extraField.setPromptText("Matrikelnummer / Fachbereich");

        ComboBox<String> rolleBox = new ComboBox<>();
        rolleBox.getItems().addAll("STUDENT", "INSTRUCTOR");
        rolleBox.setValue("STUDENT");
        rolleBox.setOnAction(e -> {
            if (rolleBox.getValue().equals("STUDENT")) {
                extraField.setPromptText("Matrikelnummer");
            } else {
                extraField.setPromptText("Fachbereich");
            }
        });

        Label fehlerLabel = new Label("");
        fehlerLabel.setTextFill(Color.web("#f38ba8"));

        Button hinzufuegenBtn = new Button("➕ Hinzufügen");
        hinzufuegenBtn.setStyle("-fx-background-color: #89b4fa; -fx-font-weight: bold;");
        hinzufuegenBtn.setOnAction(e -> {
            try {
                if (rolleBox.getValue().equals("STUDENT")) {
                    Student s = new Student(
                        nameField.getText(), emailField.getText(),
                        passField.getText(), LocalDateTime.now(),
                        extraField.getText()
                    );
                    userManager.hinzufuegen(s);
                } else {
                    Instructor i = new Instructor(
                        nameField.getText(), emailField.getText(),
                        passField.getText(), LocalDateTime.now(),
                        extraField.getText()
                    );
                    userManager.hinzufuegen(i);
                }
                tabelle.getItems().clear();
                tabelle.getItems().addAll(userManager.alleUser());
                nameField.clear();
                emailField.clear();
                passField.clear();
                extraField.clear();
                fehlerLabel.setText("");
            } catch (LMSException ex) {
                fehlerLabel.setText("❌ " + ex.getMessage());
            }
        });

        Button loeschenBtn = new Button("🗑️ Löschen");
        loeschenBtn.setStyle("-fx-background-color: #f38ba8; -fx-font-weight: bold;");
        loeschenBtn.setOnAction(e -> {
            User selected = tabelle.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    userManager.entfernen(selected.getId());
                    tabelle.getItems().clear();
                    tabelle.getItems().addAll(userManager.alleUser());
                } catch (LMSException ex) {
                    fehlerLabel.setText("❌ " + ex.getMessage());
                }
            }
        });

        HBox buttonBox = new HBox(10, hinzufuegenBtn, loeschenBtn);

        Label rolleLabel = new Label("Rolle:");
        rolleLabel.setTextFill(Color.web("#cdd6f4"));

        VBox formular = new VBox(8,
            nameField, emailField, passField,
            rolleLabel, rolleBox, extraField,
            fehlerLabel, buttonBox
        );

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #1e1e2e;");

        Label titel = new Label("👤 Benutzer");
        titel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titel.setTextFill(Color.web("#89b4fa"));

        layout.getChildren().addAll(titel, formular, tabelle);

        Scene scene = new Scene(layout, 650, 600);
        stage.setScene(scene);
        stage.show();
    }
}