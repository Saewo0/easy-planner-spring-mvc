package com.savvy.service;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class StorageServiceTest extends ServiceTest {
    @Autowired
    private StorageService storageService;

    @Autowired
    AmazonS3 s3Fake;

    @Test
    public void uploadFileTest() {
        File fakeLocalFile = Mockito.mock(File.class);
        Mockito.when(fakeLocalFile.getName()).thenReturn("fakeFileName");

        storageService.uploadFile(fakeLocalFile);

        Mockito.verify(s3Fake, Mockito.times(1)).putObject(any());
    }

    @Test
    public void getUrlTest() {
        storageService.getUrl("testS3Key");
        Mockito.verify(s3Fake, Mockito.times(1)).getUrl(anyString(), anyString());
    }
}
