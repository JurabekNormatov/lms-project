package com.lms.ui;

import com.lms.exception.LMSException;
import com.lms.manager.UserManager;
import com.lms.model.Instructor;
import com.lms.model.Student;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.time.LocalDateTime;

public class RegistrierungView {

    private UserManager userManager = UserManager.getInstance();
    private Stage primaryStage;

    public RegistrierungView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() {
        Label titelLabel = new Label("📝 Registrierung");
        titelLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titelLabel.setTextFill(Color.web("#a6e3a1"));

        // Felder
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setStyle("-fx-background-color: #313244; -fx-text-fill: white;");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setStyle("-fx-background-color: #313244; -fx-text-fill: white;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Passwort");
        passwordField.setStyle("-fx-background-color: #313244; -fx-text-fill: white;");

        TextField extraField = new TextField();
        extraField.setPromptText("Matrikelnummer / Fachbereich");
        extraField.setStyle("-fx-background-color: #313244; -fx-text-fill: white;");

        // Rolle auswählen
        ComboBox<String> rolleBox = new ComboBox<>();
        rolleBox.getItems().addAll("STUDENT", "INSTRUCTOR");
        rolleBox.setValue("STUDENT");
        rolleBox.setStyle("-fx-background-color: #313244; -fx-text-fill: white;");

        // Prompt Text ändern je nach Rolle
        rolleBox.setOnAction(e -> {
            if (rolleBox.getValue().equals("STUDENT")) {
                extraField.setPromptText("Matrikelnummer");
            } else {
                extraField.setPromptText("Fachbereich");
            }
        });

        Label fehlerLabel = new Label("");
        fehlerLabel.setTextFill(Color.web("#f38ba8"));

        Label successLabel = new Label("");
        successLabel.setTextFill(Color.web("#a6e3a1"));

        // Registrieren Button
        Button registrierenBtn = new Button("✅ Registrieren");
        registrierenBtn.setPrefWidth(200);
        registrierenBtn.setStyle("-fx-background-color: #a6e3a1; -fx-font-weight: bold;");
        registrierenBtn.setOnAction(e -> {
            try {
                if (rolleBox.getValue().equals("STUDENT")) {
                    Student student = new Student(
                        nameField.getText(),
                        emailField.getText(),
                        passwordField.getText(),
                        LocalDateTime.now(),
                        extraField.getText()
                    );
                    userManager.hinzufuegen(student);
                } else {
                    Instructor instructor = new Instructor(
                        nameField.getText(),
                        emailField.getText(),
                        passwordField.getText(),
                        LocalDateTime.now(),
                        extraField.getText()
                    );
                    userManager.hinzufuegen(instructor);
                }
                successLabel.setText("✅ Erfolgreich registriert!");
                fehlerLabel.setText("");
                nameField.clear();
                emailField.clear();
                passwordField.clear();
                extraField.clear();
            } catch (LMSException ex) {
                fehlerLabel.setText("❌ " + ex.getMessage());
                successLabel.setText("");
            }
        });

        // Zurück Button
        Button zurueckBtn = new Button("← Zurück zum Login");
        zurueckBtn.setStyle("-fx-background-color: #6c7086; -fx-text-fill: white;");
        zurueckBtn.setOnAction(e -> new LoginView(primaryStage).show());

        // Layout
        VBox layout = new VBox(12);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        layout.setStyle("-fx-background-color: #1e1e2e;");
        layout.setMaxWidth(400);

        Label rolleLabel = new Label("Rolle:");
        rolleLabel.setTextFill(Color.web("#cdd6f4"));

        layout.getChildren().addAll(
            titelLabel,
            nameField, emailField, passwordField,
            rolleLabel, rolleBox,
            extraField,
            fehlerLabel, successLabel,
            registrierenBtn, zurueckBtn
        );

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #1e1e2e;");
        root.setCenter(layout);

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}