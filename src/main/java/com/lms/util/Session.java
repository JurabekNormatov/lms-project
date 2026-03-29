package com.lms.util;

import com.lms.model.User;
import java.time.LocalDateTime;

public class Session {

    private User user;
    private LocalDateTime loginZeit;
    private LocalDateTime ablaufZeit;
    private static final int TIMEOUT_MINUTEN = 30;

    public Session(User user) {
        this.user = user;
        this.loginZeit = LocalDateTime.now();
        this.ablaufZeit = loginZeit.plusMinutes(TIMEOUT_MINUTEN);
    }

    public User getUser() { return user; }
    public LocalDateTime getLoginZeit() { return loginZeit; }

    public boolean istAbgelaufen() {
        return LocalDateTime.now().isAfter(ablaufZeit);
    }

    public void verlängern() {
        this.ablaufZeit = LocalDateTime.now().plusMinutes(TIMEOUT_MINUTEN);
    }

    @Override
    public String toString() {
        return "Session{user=" + user.getName() +
               ", loginZeit=" + loginZeit +
               ", abläuft=" + ablaufZeit + "}";
    }
}