package com.savvy.worker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProcessService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SendGridEmailService sendGridEmailService;

    @JmsListener(destination = "easy-planner")
    public void processMessage(String msg) throws IOException {
        logger.debug("Processing Message: " + msg);

        sendGridEmailService.sendEmail(msg);
    }
}
