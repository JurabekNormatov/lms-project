package com.lms.util;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LMSLogger {

    // Singleton
    private static LMSLogger instance;
    private Logger logger;

    private LMSLogger() {
        try {
            // Logger erstellen
            logger = Logger.getLogger("LMS");
            logger.setLevel(Level.ALL);

            // Konsole Handler
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.INFO);
            logger.addHandler(consoleHandler);

            // Datei Handler — speichert in lms.log
            FileHandler fileHandler = new FileHandler("lms.log", true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            logger.setUseParentHandlers(false);

        } catch (Exception e) {
            System.out.println("Logger Fehler: " + e.getMessage());
        }
    }

    public static LMSLogger getInstance() {
        if (instance == null) {
            instance = new LMSLogger();
        }
        return instance;
    }

    // Info — normaler Ablauf
    public void info(String nachricht) {
        logger.info(nachricht);
    }

    // Warning — Warnung
    public void warning(String nachricht) {
        logger.warning(nachricht);
    }

    // Severe — kritischer Fehler
    public void severe(String nachricht) {
        logger.severe(nachricht);
    }

    // Fine — Details für Debugging
    public void fine(String nachricht) {
        logger.fine(nachricht);
    }
}