package com.savvy.service;

import com.amazonaws.SdkClientException;
import com.savvy.domain.Photo;
import com.savvy.domain.User;
import com.savvy.repository.PhotoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PhotoService {

    private PhotoDao photoDao;
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public PhotoService(@Autowired PhotoDao photoDao, @Autowired UserService userService) {
        this.photoDao = photoDao;
        this.userService = userService;
    }

    @Transactional(rollbackFor = SdkClientException.class)
    public Photo savePhoto(String s3Key, String username) throws SdkClientException {
        User owner = userService.getByUsername(username);
        // TODO: process original fileName
        Photo photo = new Photo("xxx", s3Key, owner);
        photoDao.save(photo);

        return null;
    }

    @Transactional
    public List<Photo> getPhotosByUserId(long userId) {
        return photoDao.findByUserId(userId);
    }
}
