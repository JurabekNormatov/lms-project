package com.lms.ui;

import com.lms.exception.LMSException;
import com.lms.manager.UserManager;
import com.lms.model.User;
import com.lms.util.SessionManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginView {

    private UserManager userManager = UserManager.getInstance();
    private Stage primaryStage;

    public LoginView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() {
        Label titelLabel = new Label("🎓 LMS Login");
        titelLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titelLabel.setTextFill(Color.web("#89b4fa"));

        Label untertitel = new Label("Bitte einloggen oder registrieren");
        untertitel.setTextFill(Color.web("#cdd6f4"));

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setStyle("-fx-background-color: #313244; -fx-text-fill: white;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Passwort");
        passwordField.setStyle("-fx-background-color: #313244; -fx-text-fill: white;");

        Label fehlerLabel = new Label("");
        fehlerLabel.setTextFill(Color.web("#f38ba8"));

        Button loginBtn = new Button("🔑 Einloggen");
        loginBtn.setPrefWidth(200);
        loginBtn.setStyle("-fx-background-color: #89b4fa; -fx-font-weight: bold; -fx-font-size: 14px;");
        loginBtn.setOnAction(e -> {
            try {
                User user = userManager.findeNachEmail(emailField.getText());
                if (user.getPassword().equals(passwordField.getText())) {
                    fehlerLabel.setText("");
                    // Session erstellen!
                    String sessionId = SessionManager.getInstance().sessionErstellen(user);
                    System.out.println("✅ Session erstellt: " + sessionId);
                    // Hauptmenü zeigen
                    new MainApp().zeigeHauptmenue(primaryStage);
                } else {
                    fehlerLabel.setText("❌ Falsches Passwort!");
                }
            } catch (LMSException ex) {
                fehlerLabel.setText("❌ Email nicht gefunden!");
            } catch (Exception ex) {
                fehlerLabel.setText("❌ Fehler: " + ex.getMessage());
            }
        });

        Button registrierenBtn = new Button("📝 Registrieren");
        registrierenBtn.setPrefWidth(200);
        registrierenBtn.setStyle("-fx-background-color: #a6e3a1; -fx-font-weight: bold; -fx-font-size: 14px;");
        registrierenBtn.setOnAction(e -> new RegistrierungView(primaryStage).show());

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50));
        layout.setStyle("-fx-background-color: #1e1e2e;");
        layout.setMaxWidth(400);

        layout.getChildren().addAll(
            titelLabel, untertitel,
            new Separator(),
            emailField, passwordField,
            fehlerLabel,
            loginBtn, registrierenBtn
        );

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #1e1e2e;");
        root.setCenter(layout);

        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}