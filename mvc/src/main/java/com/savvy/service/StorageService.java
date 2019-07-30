package com.savvy.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;


@Service
public class StorageService {

    private AmazonS3 s3Client;
    private String bucketName;

    private static final long expTimeMillis = 1000 * 15;
    private static final String homeDir = System.getProperty("user.dir");

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public StorageService(@Autowired AmazonS3 s3Client, @Value("${aws.bucketName}") String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    /****************************************** HELPER METHODS ******************************************/

    private String getS3Key(String fileName) {
        UUID uuid = UUID.randomUUID();
        String baseName = FilenameUtils.getBaseName(fileName);
        String extension = FilenameUtils.getExtension(fileName);
        return baseName + "-" + uuid + "." + extension;
    }

    /****************************************** PUBLIC METHODS ******************************************/
    public File transfer2LocalFile(MultipartFile file) throws IOException {
        String tmpFilePath = homeDir + file.getOriginalFilename();
        File tmpFile = new File(tmpFilePath);
        file.transferTo(tmpFile);

        return tmpFile;
    }

    public String uploadFile(File file) throws SdkClientException, AmazonServiceException {
        String s3Key = getS3Key(file.getName());
        PutObjectRequest request = new PutObjectRequest(this.bucketName, s3Key, file);
        s3Client.putObject(request);

        return s3Key;
    }

    public void deleteFile(String s3Key) throws SdkClientException, AmazonServiceException {
        DeleteObjectRequest request = new DeleteObjectRequest(this.bucketName, s3Key);
        s3Client.deleteObject(request);
    }

    public URL getUrl(String s3Key) {
        return s3Client.getUrl(this.bucketName, s3Key);
    }

    //TODO getObject: clear exception handler
    public void getObject(String s3Key) {
        GetObjectRequest request = new GetObjectRequest(this.bucketName, s3Key);
        S3Object object = s3Client.getObject(request);
        S3ObjectInputStream s3IS = object.getObjectContent();
        try {
            String filePath = homeDir + s3Key;
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

    public URL getPreSignedUrl(String s3Key){
        Date expiration = new java.util.Date();
        expiration.setTime(expiration.getTime() + expTimeMillis);
        logger.debug("Generating pre-signed URL for " + s3Key + " in bucket " + this.bucketName);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(this.bucketName, s3Key)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);
        return s3Client.generatePresignedUrl(generatePresignedUrlRequest);
    }
}
