package com.savvy.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;


@Service
public class StorageService {
    private AmazonS3 s3Client;
    private static final long expTimeMillis = 1000 * 15;
    private static final String outputFilePath = "";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public StorageService(@Autowired AmazonS3 s3) {
        this.s3Client = s3;
    }

    public void uploadObject(String bucketName, String s3Key, File file) {
        PutObjectRequest request = new PutObjectRequest(bucketName, s3Key, file);
        s3Client.putObject(request);

    }

    public URL getUrl(String bucketName, String s3Key) {
        return s3Client.getUrl(bucketName, s3Key);
    }

    //TODO getObject: clear exception handler
    public void getObject(String bucketName, String fileName) {
        GetObjectRequest request = new GetObjectRequest(bucketName, fileName);
        S3Object object = s3Client.getObject(request);
        S3ObjectInputStream s3IS = object.getObjectContent();
        try {
            String filePath = outputFilePath + fileName;
            FileOutputStream fOS = new FileOutputStream(new File(filePath));
            byte[] read_buf = new byte[1024];
            int read_len = 0;
            while ((read_len = s3IS.read(read_buf)) > 0) {
                fOS.write(read_buf, 0, read_len);
            }
            s3IS.close();
            fOS.close();
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public URL getPreSignedUrl(String bucketName, String fileName){
        Date expiration = new java.util.Date();
        expiration.setTime(expiration.getTime() + this.expTimeMillis);
        logger.debug("Generating pre-signed URL for " + fileName + " in bucket " + bucketName);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);
        return s3Client.generatePresignedUrl(generatePresignedUrlRequest);
    }
}
