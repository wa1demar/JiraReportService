package com.swansoftwaresolutions.jirareport.core.service;

import javax.mail.MessagingException;
import java.util.Map;

/**
 * @author Vladimir Martynyuk
 */
public interface ApplicationMailer {

    void sendMail(String to, String subject, Map properties, String velocity) throws MessagingException;
    void sendPreConfiguredMail(String message);
}
