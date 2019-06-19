package com.savvy.worker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProcessService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SendGridEmailService sendGridEmailService;

    @JmsListener(destination = "easy-planner")
    public void processMessage(String msg) throws IOException {
        logger.debug("Processing Message: " + msg);

        Map<String, String> fakeRequest = new HashMap<>();
        fakeRequest.put("username","Ryo");
        fakeRequest.put("avatar","https://easy-planner.s3.amazonaws.com/Wechat_Icon.jpg");
        fakeRequest.put("attached_text", "Hi, I'm the founder of ASCENDING LLC.");
        fakeRequest.put("request_link", "");

        Map<String, Object> fakeMessage = new HashMap<>();
        fakeMessage.put("subject", "Friend Invitation");
        fakeMessage.put("from", "test@example.com");
        fakeMessage.put("to_emails", Arrays.asList("zhangxwsavvy@gmail.com", "xinwei.z@columbia.edu"));
        fakeMessage.put("to_usernames", Arrays.asList("SavvyZ", "Xinwei"));
        fakeMessage.put("request", fakeRequest);

        String fakeMessageJson = new ObjectMapper().writeValueAsString(fakeMessage);

        logger.debug(fakeMessageJson);

        sendGridEmailService.sendEmail(fakeMessageJson);
    }
}
