package com.savvy.repository;

import com.savvy.domain.User;

import java.util.List;

public interface UserDao {
    User save(User user);

    List<User> findAll();

    User findByIdEager(Long id);

    User findById(Long id);

    User findByUsernameIgnoreCase(String username);
}
