package com.savvy.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;

@Service
public class StorageService {
    private AmazonS3 s3Client;

    public StorageService(@Autowired AmazonS3 s3) {
        this.s3Client = s3;
    }

    public void uploadObject(String bucketName, File file) {
        PutObjectRequest request = new PutObjectRequest(bucketName, file.getName(), file);
        s3Client.putObject(request);
    }

    public URL getUrl(String bucketName, String fileName) {
        return s3Client.getUrl(bucketName, fileName);
    }
}
