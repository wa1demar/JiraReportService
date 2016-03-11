package com.swansoftwaresolutions.jirareport.domain.entity.builder;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;

/**
 * @author Vladimir Martynyuk
 */
public class JiraUserBuilder {

    private JiraUser user;

    public JiraUserBuilder() {
        this.user = new JiraUser();
    }

    public JiraUserBuilder login(String login) {
        user.setLogin(login);
        return this;
    }

    public JiraUser build() {
        return user;
    }
}
