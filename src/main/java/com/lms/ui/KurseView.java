package com.lms.ui;

import com.lms.exception.LMSException;
import com.lms.manager.CourseManager;
import com.lms.model.Course;
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

public class KurseView {

    private CourseManager courseManager = CourseManager.getInstance();

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Kurse Verwaltung");

        // Tabelle
        TableView<Course> tabelle = new TableView<>();

        TableColumn<Course, String> titelCol = new TableColumn<>("Titel");
        titelCol.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getTitel()));

        TableColumn<Course, String> kategorieCol = new TableColumn<>("Kategorie");
        kategorieCol.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getKategorie()));

        TableColumn<Course, String> schwierigkeitCol = new TableColumn<>("Schwierigkeit");
        schwierigkeitCol.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getSchwierigkeitsgrad()));

        TableColumn<Course, String> preisCol = new TableColumn<>("Preis");
        preisCol.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getPreis() + " €"));

        tabelle.getColumns().addAll(titelCol, kategorieCol, schwierigkeitCol, preisCol);
        tabelle.getItems().addAll(courseManager.alleKurse());

        // Formular
        TextField titelField      = new TextField();
        TextField kategorieField  = new TextField();
        TextField preisField      = new TextField();
        titelField.setPromptText("Titel");
        kategorieField.setPromptText("Kategorie");
        preisField.setPromptText("Preis");

        ComboBox<String> schwierigkeitBox = new ComboBox<>();
        schwierigkeitBox.getItems().addAll("ANFAENGER", "MITTEL", "FORTGESCHRITTEN");
        schwierigkeitBox.setValue("ANFAENGER");

        Label fehlerLabel = new Label("");
        fehlerLabel.setTextFill(Color.web("#f38ba8"));

        // Hinzufügen Button
        Button hinzufuegenBtn = new Button("➕ Hinzufügen");
        hinzufuegenBtn.setStyle("-fx-background-color: #a6e3a1; -fx-font-weight: bold;");
        hinzufuegenBtn.setOnAction(e -> {
            try {
                double preis = Double.parseDouble(preisField.getText());
                Course c = new Course(
                    titelField.getText(), "",
                    kategorieField.getText(),
                    schwierigkeitBox.getValue(),
                    preis, 0, "", LocalDateTime.now()
                );
                courseManager.hinzufuegen(c);
                tabelle.getItems().clear();
                tabelle.getItems().addAll(courseManager.alleKurse());
                titelField.clear();
                kategorieField.clear();
                preisField.clear();
                fehlerLabel.setText("");
            } catch (LMSException ex) {
                fehlerLabel.setText("❌ " + ex.getMessage());
            } catch (NumberFormatException ex) {
                fehlerLabel.setText("❌ Preis muss eine Zahl sein!");
            }
        });

        // Löschen Button
        Button loeschenBtn = new Button("🗑️ Löschen");
        loeschenBtn.setStyle("-fx-background-color: #f38ba8; -fx-font-weight: bold;");
        loeschenBtn.setOnAction(e -> {
            Course selected = tabelle.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    courseManager.entfernen(selected.getId());
                    tabelle.getItems().clear();
                    tabelle.getItems().addAll(courseManager.alleKurse());
                } catch (LMSException ex) {
                    fehlerLabel.setText("❌ " + ex.getMessage());
                }
            }
        });

        // Suche
        TextField suchField = new TextField();
        suchField.setPromptText("🔍 Kurs suchen...");
        Button suchenBtn = new Button("Suchen");
        suchenBtn.setStyle("-fx-background-color: #cba6f7; -fx-font-weight: bold;");
        suchenBtn.setOnAction(e -> {
            tabelle.getItems().clear();
            tabelle.getItems().addAll(courseManager.suchen(suchField.getText()));
        });

        Button alleAnzeigenBtn = new Button("Alle anzeigen");
        alleAnzeigenBtn.setStyle("-fx-background-color: #6c7086; -fx-text-fill: white;");
        alleAnzeigenBtn.setOnAction(e -> {
            tabelle.getItems().clear();
            tabelle.getItems().addAll(courseManager.alleKurse());
            suchField.clear();
        });

        HBox suchBox = new HBox(10, suchField, suchenBtn, alleAnzeigenBtn);
        HBox buttonBox = new HBox(10, hinzufuegenBtn, loeschenBtn);

        Label schwLabel = new Label("Schwierigkeit:");
        schwLabel.setTextFill(Color.web("#cdd6f4"));

        VBox formular = new VBox(8,
            titelField, kategorieField,
            schwLabel, schwierigkeitBox,
            preisField, fehlerLabel, buttonBox
        );

        VBox layout = new VBox(12);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #1e1e2e;");

        Label titel = new Label("📚 Kurse");
        titel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titel.setTextFill(Color.web("#a6e3a1"));

        layout.getChildren().addAll(titel, formular, suchBox, tabelle);

        Scene scene = new Scene(layout, 700, 650);
        stage.setScene(scene);
        stage.show();
    }
}