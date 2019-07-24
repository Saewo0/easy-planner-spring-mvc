package com.savvy.service;

import com.amazonaws.SdkClientException;
import com.savvy.domain.Photo;
import com.savvy.domain.User;
import com.savvy.repository.PhotoDao;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Service
public class PhotoService {
    private static final String homeDir = System.getProperty("user.dir");
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected PhotoDao photoDao;

    @Autowired
    private UserService userService;

    @Autowired
    private StorageService storageService;

    private String getS3Key(String originFileName) {
        UUID uuid = UUID.randomUUID();
        String baseName = FilenameUtils.getBaseName(originFileName);
        String extension = FilenameUtils.getExtension(originFileName);
        return baseName + "-" + uuid + extension;
    }

//    private File multipart2File(MultipartFile multipartFile) {
//
//    }

    @Transactional(rollbackFor = SdkClientException.class)
    public Photo savePhoto(MultipartFile file, String username) throws SdkClientException {
        User owner = userService.getByUsername(username);
        String originFileName = file.getOriginalFilename();

        try {
            String s3Key = getS3Key(originFileName);
            String tmpFilePath = homeDir + s3Key;
            Photo photo = new Photo(originFileName, s3Key, owner);
            photoDao.save(photo);
            File tmpFile = new File(tmpFilePath);
            file.transferTo(tmpFile);
            storageService.uploadObject("easy-planner", s3Key, tmpFile);
            return photo;
        } catch (IOException e) {
            // TODO: throw an HTTP STATUS CODE
        }

        return null;
    }
}
