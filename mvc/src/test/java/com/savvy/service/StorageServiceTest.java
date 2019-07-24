package com.savvy.service;

import com.amazonaws.services.s3.AmazonS3;
import com.savvy.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.net.MalformedURLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("unit")
public class StorageServiceTest {
    @Autowired
    private StorageService storageService;

    @Autowired
    AmazonS3 s3Fake;

    @Test
    public void uploadObjectTest() {
        File testFile = new File("images/laowang.png");
        storageService.uploadObject("xxxxx", "laowang-31947161.png", testFile);
        Mockito.verify(s3Fake, Mockito.times(1)).putObject(any());
    }

    @Test
    public void getUrlTest() throws MalformedURLException {
        storageService.getUrl("xxxxx", "laowang.png");
        Mockito.verify(s3Fake, Mockito.times(1)).getUrl(anyString(), anyString());
    }
}
