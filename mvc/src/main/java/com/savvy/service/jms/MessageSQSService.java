package com.savvy.service.jms;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class MessageSQSService {

    private AmazonSQS sqsClient;
    private String queueUrl;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public MessageSQSService(@Autowired AmazonSQS sqsClient, @Value("${jms.queue.name}") String queueName) {
        logger.debug("messageSQSService constructor");
        this.sqsClient = sqsClient;
        this.queueUrl = getQueueUrl(queueName);
    }

    private String getQueueUrl(String sqsName) {
        logger.debug("messageSQSService getQueueUrl: " + sqsName + this.sqsClient.listQueues().toString());
        String queueUrl = this.sqsClient.getQueueUrl(sqsName).getQueueUrl();
        logger.debug("messageSQSService getQueueUrl result: " + queueUrl);
        return queueUrl;
    }

    public void sendMessage(String message) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(message);
        sqsClient.sendMessage(sendMessageRequest);
    }

}
