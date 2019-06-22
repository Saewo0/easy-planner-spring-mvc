package com.savvy.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.ArgumentMatchers.anyString;

@Configuration
public class MockConfig {

    @Bean
    @Profile("unit")
    public AmazonS3 getAmazonS3Bean() throws MalformedURLException {
        AmazonS3 s3Fake = Mockito.mock(AmazonS3.class);
        Mockito.when(s3Fake.getUrl(anyString(), anyString())).thenReturn(new URL("http://IP:4567/foldername/1234?abc=xyz"));
        return s3Fake;
    }

    @Bean
    @Profile("unit")
    public AmazonSQS getAmazonSQSBean() {
        AmazonSQS sqsFake = Mockito.mock(AmazonSQS.class);
        GetQueueUrlResult fakeResult = new GetQueueUrlResult();
        Mockito.when(sqsFake.getQueueUrl(anyString())).thenReturn(fakeResult);
        return sqsFake;
    }
}
