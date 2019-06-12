package com.savvy.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = "com.savvy", excludeFilters = @ComponentScan.Filter(type= FilterType.REGEX, pattern = "com.savvy.api.*"))
public class AppConfig {

    @Bean
    @Profile({"dev", "test", "stage", "prod"})
    public AmazonS3 getAmazonS3Bean() {
        String clientRegion = "us-east-1";
        AmazonS3 s3Client = AmazonS3ClientBuilder
                            .standard()
                            .withRegion(clientRegion)
                            .withCredentials(new DefaultAWSCredentialsProviderChain())
                            .build();
        return s3Client;
    }
}
