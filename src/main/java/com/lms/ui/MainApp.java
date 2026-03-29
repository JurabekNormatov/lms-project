package com.lms.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import com.lms.manager.CourseManager;
import com.lms.manager.UserManager;

public class MainApp extends Application {

    private UserManager userManager = UserManager.getInstance();
    private CourseManager courseManager = CourseManager.getInstance();

    @Override
    public void start(Stage primaryStage) {
        // Daten aus DB laden
        userManager.ausDatenbankLaden();
        courseManager.ausDatenbankLaden();

        // Zuerst Login zeigen!
        new LoginView(primaryStage).show();
    }

    // Hauptmenü — wird nach erfolgreichem Login aufgerufen
    public void zeigeHauptmenue(Stage primaryStage) {

        // Hauptlayout
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #1e1e2e;");

        // Titel
        VBox titelBox = new VBox(10);
        titelBox.setAlignment(Pos.CENTER);
        titelBox.setPadding(new Insets(30));

        Label titelLabel = new Label("🎓 LMS");
        titelLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        titelLabel.setTextFill(Color.web("#89b4fa"));

        Label untertitelLabel = new Label("Lernmanagementsystem");
        untertitelLabel.setFont(Font.font("Arial", 18));
        untertitelLabel.setTextFill(Color.web("#cdd6f4"));

        titelBox.getChildren().addAll(titelLabel, untertitelLabel);

        // Buttons Grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(20));

        // Karten erstellen
        VBox benutzerKarte   = erstelleKarte("👤", "Benutzer",        "#89b4fa");
        VBox kurseKarte      = erstelleKarte("📚", "Kurse",           "#a6e3a1");
        VBox einschreibKarte = erstelleKarte("📝", "Einschreibungen", "#cba6f7");
        VBox bewertungKarte  = erstelleKarte("⭐", "Bewertungen",     "#fab387");

        // Klick Aktionen
        benutzerKarte.setOnMouseClicked(e -> new BenutzerView().show());
        kurseKarte.setOnMouseClicked(e -> new KurseView().show());
        einschreibKarte.setOnMouseClicked(e -> new EinschreibungenView().show());
        bewertungKarte.setOnMouseClicked(e -> new BewertungenView().show());

        grid.add(benutzerKarte, 0, 0);
        grid.add(kurseKarte, 1, 0);
        grid.add(einschreibKarte, 0, 1);
        grid.add(bewertungKarte, 1, 1);

        // Footer
        Label footer = new Label("© 2026 LMS — Jurabek Normatov");
        footer.setTextFill(Color.web("#6c7086"));
        footer.setFont(Font.font("Arial", 12));
        BorderPane.setAlignment(footer, Pos.CENTER);
        BorderPane.setMargin(footer, new Insets(10));

        root.setTop(titelBox);
        root.setCenter(grid);
        root.setBottom(footer);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("LMS — Lernmanagementsystem");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox erstelleKarte(String emoji, String titel, String farbe) {
        VBox karte = new VBox(10);
        karte.setAlignment(Pos.CENTER);
        karte.setPrefSize(200, 150);
        karte.setStyle(
            "-fx-background-color: #313244;" +
            "-fx-border-color: " + farbe + ";" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;"
        );
        karte.setCursor(javafx.scene.Cursor.HAND);

        Label emojiLabel = new Label(emoji);
        emojiLabel.setFont(Font.font("Arial", 36));

        Label titelLabel = new Label(titel);
        titelLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titelLabel.setTextFill(Color.web(farbe));

        karte.getChildren().addAll(emojiLabel, titelLabel);

        karte.setOnMouseEntered(e -> karte.setStyle(
            "-fx-background-color: #45475a;" +
            "-fx-border-color: " + farbe + ";" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;"
        ));
        karte.setOnMouseExited(e -> karte.setStyle(
            "-fx-background-color: #313244;" +
            "-fx-border-color: " + farbe + ";" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;"
        ));

        return karte;
    }

    public static void main(String[] args) {
        launch(args);
    }
}