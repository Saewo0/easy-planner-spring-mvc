package com.savvy.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = "com.savvy", excludeFilters = @ComponentScan.Filter(type= FilterType.REGEX, pattern = "com.savvy.api.*"))
public class AppConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    @Profile({"dev", "test", "stage", "prod"})
    public AmazonS3 getAmazonS3Bean() {
        logger.debug("getAmazonS3Bean...");
        return AmazonS3ClientBuilder
            .standard()
            .withCredentials(new DefaultAWSCredentialsProviderChain())
            .build();
    }

    @Bean
    @Profile({"dev", "test", "stage", "prod"})
    public AmazonSQS getAmazonSQSBean() {
        logger.debug("getAmazonSQSBean...");
        return AmazonSQSClientBuilder
            .standard()
            .withCredentials(new DefaultAWSCredentialsProviderChain())
            .build();
    }
}
