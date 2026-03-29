package com.lms.manager;

import java.util.HashMap;
import java.util.List;
import com.lms.model.Lesson;

import com.lms.util.LMSLogger;

public class FortschrittManager {
	
	// Singleton
    private static FortschrittManager instance;

    // HashMap — Key=id, Value=Course
    private HashMap<String, Boolean> abgeschlosseneLektion = new HashMap<>();
    

    private FortschrittManager() {}
    
    private LMSLogger logger = LMSLogger.getInstance();
    
    public static FortschrittManager getInstance() {
        if (instance == null) {
            instance = new FortschrittManager();
        }
        return instance;
    }
    
    public void lektionAbschliessen(int studentId, int lessonId) {
    	String key = studentId + "_" + lessonId;
        abgeschlosseneLektion.put(key, true);
        logger.info("Lektion abgeschlossen: Student " + studentId + 
                    " Lektion " + lessonId);
    }
    
    public boolean istAbgeschlossen(int studentId, int lessonId) {
    	String key = studentId + "_" + lessonId;
        return abgeschlosseneLektion.getOrDefault(key, false);
    }
    
    public double fortschrittBerechnen(int studentId, List<Lesson> lektionen) {
    	int abgeschlossen = 0;
        for (Lesson l : lektionen) {
            if (istAbgeschlossen(studentId, l.getId())) {
                abgeschlossen++;
            }
        }
        return lektionen.isEmpty() ? 0.0 :
               (double) abgeschlossen / lektionen.size() * 100;
    }

}
