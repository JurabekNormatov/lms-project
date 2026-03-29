package com.lms.manager;

import com.lms.exception.LMSException;
import com.lms.util.LMSLogger;
import com.lms.model.User;
import com.lms.db.UserDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserManager {

    private static UserManager instance;
    private HashMap<Integer, User> users = new HashMap<>();
    private int nextId = 1;
    private UserDAO userDAO = new UserDAO();
    private LMSLogger logger = LMSLogger.getInstance();

    private UserManager() {}

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void hinzufuegen(User user) throws LMSException {
        for (User u : users.values()) {
            if (u.getEmail().equals(user.getEmail())) {
                throw new LMSException("Email bereits vorhanden: " + user.getEmail());
            }
        }
        // Erst ID setzen dann speichern!
        user.setId(nextId);
        users.put(nextId, user);
        nextId++;
        userDAO.speichern(user);
        logger.info("User hinzugefügt: " + user.getName());
    }

    public void ausDatenbankLaden() {
        List<User> dbUsers = userDAO.alleUser();
        for (User u : dbUsers) {
            users.put(u.getId(), u);
            if (u.getId() >= nextId) {
                nextId = u.getId() + 1;
            }
        }
        logger.info(dbUsers.size() + " User aus DB geladen!");
    }

    public void entfernen(int id) throws LMSException {
        if (!users.containsKey(id)) {
            throw new LMSException("User nicht gefunden: ID " + id);
        }
        users.remove(id);
        logger.info("User entfernt: ID " + id);
    }

    public User findeNachId(int id) throws LMSException {
        User user = users.get(id);
        if (user == null) {
            throw new LMSException("User nicht gefunden: ID " + id);
        }
        return user;
    }

    public User findeNachEmail(String email) throws LMSException {
        for (User u : users.values()) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        throw new LMSException("User nicht gefunden: " + email);
    }

    public List<User> alleUser() {
        return new ArrayList<>(users.values());
    }
}