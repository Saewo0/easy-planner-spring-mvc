package com.savvy.api.v1;

import com.amazonaws.SdkClientException;
import com.savvy.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping(value = "/api/file")
@ResponseBody
public class StorageController {

    @Autowired
    private StorageService storageService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(method = RequestMethod.POST, params = {"isPublic"})
    public String uploadFile(@RequestParam(name = "file") MultipartFile file, @RequestParam(name = "isPublic") boolean isPublic) {
        logger.debug(file.getOriginalFilename());

        try {
            File localFile = storageService.transfer2LocalFile(file);
            String s3Key = storageService.uploadFile(localFile);
            if (isPublic) {
                // public url available
                return storageService.getUrl(s3Key).toString();
            } else {
                // private file, return s3Key
                return s3Key;
            }
        } catch (IOException ioe) {
            logger.error("storageService IOException occurs", ioe);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "tmpFile Creation Failed");
        } catch (SdkClientException sce) {
            logger.error("Amazon S3 SdkClientException occurs", sce);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "file uploading Failed");
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void removeFile(@RequestParam(name = "file") String s3Key) {
        logger.debug("deleting file " + s3Key);

        try {
            storageService.deleteFile(s3Key);
        } catch (SdkClientException sce) {
            logger.error("Amazon S3 SdkClientException occurs", sce);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "file uploading Failed");
        }
    }
}
