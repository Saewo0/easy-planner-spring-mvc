package com.savvy.repository;

import com.savvy.domain.User;

import java.util.List;

public interface UserDao extends CRUDDao<User, Long> {

    User findByIdEager(Long id);

    User findByUsername(String username);

    User findByEmailIgnoreCase(String email);

    List<User> findByFirstNameIgnoreCase(String username);

    List<User> findByLastNameIgnoreCase(String username);
}