package com.savvy.worker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.Arrays;

@SpringBootApplication
public class WorkerApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(WorkerApplication.class, args);
//        ApplicationContext app = SpringApplication.run(WorkerApplication.class, args);
//        SQSMessageService messageService = app.getBean(SQSMessageService.class);
//        messageService.receiveMessage();
//        SendGridEmailService sendGridEmailService = app.getBean(SendGridEmailService.class);
//
//        try {
//            sendGridEmailService.sendEmail("test@example.com", "zhangxwsavvy@gmail.com", "Template Test");
//        } catch (IOException ex) {
//            throw ex;
//        }
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

}