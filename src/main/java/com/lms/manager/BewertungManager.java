package com.lms.manager;

import java.util.HashMap;
import com.lms.exception.LMSException;
import com.lms.model.Bewertung;
import com.lms.util.LMSLogger;

import java.util.ArrayList;
import java.util.List;

public class BewertungManager {
	
	// Singleton
    private static BewertungManager instance;

    // HashMap — Key=id, Value=Course
    private HashMap<Integer, Bewertung> bewertungen = new HashMap<>();
    private int nextId = 1;

    private BewertungManager() {}
    
    private LMSLogger logger = LMSLogger.getInstance();
    
    public static BewertungManager getInstance() {
        if (instance == null) {
            instance = new BewertungManager();
        }
        return instance;
    }
    
    public void bewerten(Bewertung b) throws LMSException{
    	// Sterne validieren
        if (b.getSterne() < 1 || b.getSterne() > 5) {
            throw new LMSException("Sterne müssen zwischen 1 und 5 sein!");
        }
        // Duplikat prüfen
        for (Bewertung bew : bewertungen.values()) {
            if (bew.getStudentId() == b.getStudentId() &&
                bew.getCourseId() == b.getCourseId()) {
                throw new LMSException("Student hat diesen Kurs bereits bewertet!");
            }
        }
        b.setId(nextId);
        bewertungen.put(nextId, b);
        nextId++;
        logger.info("Bewertung hinzugefügt: " + b.getSterne() + " Sterne");
    }
    
    public double durchschnitt(int courseId){
    	double summe = 0;
    	int anzahl = 0;
    	for (Bewertung b : bewertungen.values()) {
    	    if (b.getCourseId() == courseId) {
    	        summe += b.getSterne();
    	        anzahl++;
    	    }
    	}
    	return anzahl > 0 ? summe / anzahl : 0.0;
    }
    
    public List<Bewertung> bewertungenFuerKurs(int courseId){
    	List<Bewertung> ergebnis = new ArrayList<>();
        for (Bewertung b : bewertungen.values()) {
            if (b.getCourseId() == courseId) {
                ergebnis.add(b);
            }
        }
        return ergebnis;
    }

}
