package com.savvy.repository;

import com.savvy.domain.Authority;

import java.util.List;

public interface AuthorityDao extends CRUDDao<Authority, Long> {
    public List<Authority> findByUserId(Long userId);
}
