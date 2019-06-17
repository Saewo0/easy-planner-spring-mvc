package com.savvy.worker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;

import java.io.IOException;

public class ProcessService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @JmsListener(destination = "easy-planner")
    public void processMessage(String msg) throws IOException {
        logger.debug("Processing Message: " + msg);
    }
}
