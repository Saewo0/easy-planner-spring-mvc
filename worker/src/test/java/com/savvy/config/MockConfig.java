package com.savvy.config;

import com.amazonaws.services.sqs.AmazonSQS;
import com.sendgrid.SendGrid;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class MockConfig {

    @Bean
    @Profile("unit")
    public AmazonSQS getAmazonSQSBean() {
        AmazonSQS sqsFake = Mockito.mock(AmazonSQS.class);
        return sqsFake;
    }

    @Bean
    @Profile("unit")
    public SendGrid getSendGridBean() {
        SendGrid sendGridFake = Mockito.mock(SendGrid.class);
        return sendGridFake;
    }
}
