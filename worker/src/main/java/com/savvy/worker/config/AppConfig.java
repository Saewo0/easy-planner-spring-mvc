package com.savvy.worker.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "test", "stage", "prod"})
public class AppConfig {

    @Bean
    public SendGrid getSendGridBean(@Value("${sendgrid.apiKey}") String apiKey){
        return new SendGrid(apiKey);
    }

    @Bean
    public AmazonSQS getAmazonSQSBean() {
        return AmazonSQSClientBuilder
                .standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();
    }
}