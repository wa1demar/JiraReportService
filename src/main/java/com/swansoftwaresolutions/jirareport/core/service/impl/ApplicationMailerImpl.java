package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.service.ApplicationMailer;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class ApplicationMailerImpl implements ApplicationMailer {

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    private SimpleMailMessage preConfiguredMessage;

    @Autowired
    VelocityEngine velocityEngine;

    @Override
    public void sendMail(String to, String subject, Map properties, String velosity) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setFrom("garage.auto.serv@gmail.com");

        String text_ = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velosity + ".vm", properties);

        mimeMessageHelper.setText(text_, true);
        mailSender.send(mimeMessage);

    }

    @Override
    public void sendPreConfiguredMail(String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
}
