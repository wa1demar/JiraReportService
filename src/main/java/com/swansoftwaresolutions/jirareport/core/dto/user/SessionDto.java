package com.swansoftwaresolutions.jirareport.core.dto.user;

/**
 * @author Vladimir Martynyuk
 */
public class SessionDto {
    private String username;
    private String sessionId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
