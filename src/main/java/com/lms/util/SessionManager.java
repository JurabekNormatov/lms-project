package com.lms.util;

import com.lms.model.User;
import java.util.HashMap;
import java.util.Map;

public class SessionManager {

    private static SessionManager instance;
    private Map<String, Session> sessions = new HashMap<>();
    private LMSLogger logger = LMSLogger.getInstance();

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Session erstellen
    public String sessionErstellen(User user) {
        String sessionId = user.getId() + "_" + System.currentTimeMillis();
        Session session = new Session(user);
        sessions.put(sessionId, session);
        logger.info("Session erstellt für: " + user.getName());
        return sessionId;
    }

    // Session prüfen
    public boolean istGueltig(String sessionId) {
        Session session = sessions.get(sessionId);
        if (session == null) return false;
        if (session.istAbgelaufen()) {
            sessions.remove(sessionId);
            logger.info("Session abgelaufen: " + sessionId);
            return false;
        }
        session.verlängern();
        return true;
    }

    // Session beenden
    public void sessionBeenden(String sessionId) {
        sessions.remove(sessionId);
        logger.info("Session beendet: " + sessionId);
    }

    // Aktive Sessions
    public int aktiveSessionsAnzahl() {
        return sessions.size();
    }

    // User aus Session holen
    public User getUserAusSession(String sessionId) {
        Session session = sessions.get(sessionId);
        if (session != null && !session.istAbgelaufen()) {
            return session.getUser();
        }
        return null;
    }
}