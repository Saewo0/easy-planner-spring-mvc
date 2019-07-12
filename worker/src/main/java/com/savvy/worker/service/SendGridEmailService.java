package com.savvy.worker.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Service
public class SendGridEmailService {

    private SendGrid sg;

    private String friendInvitationTemplateId = "d-2fa99538dbe2472a98f94ec60785368d";
    private String eventInvitationTemplateId = "d-e6690c7671754f06a2b582e1617e9c24";
    private String registrationTemplateId = "d-1fbf46dd5f394e6b96afa75c4508cea3";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public SendGridEmailService(@Autowired SendGrid sendGrid) {
        this.sg = sendGrid;
    }


    public void sendEmail(String emailJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> emailDetails = mapper.readValue(emailJson, new TypeReference<Map<String, Object>>(){});
        String subject = emailDetails.get("subject").toString();

        Mail mail = new Mail();

        if (subject.equals("Friend Invitation")) {
            mail.setTemplateId(friendInvitationTemplateId);
        } else if (subject.equals("Event Invitation")) {
            mail.setTemplateId(eventInvitationTemplateId);
        } else if (subject.equals("Registration Confirmation")) {
            mail.setTemplateId(registrationTemplateId);
        } else {
            logger.debug("Unsupported Email Request Type!");
            return;
        }

        mail.setFrom(new Email(emailDetails.get("from").toString()));

        Map<String, String> requestInfo = (Map<String, String>) emailDetails.get("request");
        List<String> toEmails = (List<String>) emailDetails.get("to_emails");
        List<String> toUsernames = (List<String>) emailDetails.get("to_usernames");

        Personalization personalization = new Personalization();

        for (String to : toEmails) {
            personalization.addTo(new Email(to));
        }

        personalization.setSubject(subject);
        personalization.addDynamicTemplateData("username", toUsernames.get(0));
        personalization.addDynamicTemplateData("request", requestInfo);

        mail.addPersonalization(personalization);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }
}