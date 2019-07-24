package com.savvy.repository;

import com.savvy.domain.Photo;

import java.util.List;

public interface PhotoDao extends CRUDDao<Photo, Long> {
    List<Photo> findByUserId(Long userId);
}