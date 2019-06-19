package com.savvy.worker.config;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AppConfig {

    @Value("${sendgrid.apiKey}")
    private String apiKey;

    @Bean
    @Profile({"dev", "test", "stage", "prod"})
    public SendGrid getSendGridBean(){
        return new SendGrid(apiKey);
    }
}