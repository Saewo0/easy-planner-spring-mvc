package com.savvy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.savvy.domain.Authority;
import com.savvy.domain.AuthorityRole;
import com.savvy.domain.User;
import com.savvy.repository.AuthorityDao;
import com.savvy.repository.UserDao;
import com.savvy.service.jms.MessageSQSService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private UserDao userDao;
    private AuthorityDao authorityDao;
    private MessageSQSService sqsService;
    private BCryptPasswordEncoder encoder;

    public UserService(@Autowired UserDao userDao, @Autowired AuthorityDao authorityDao, @Autowired MessageSQSService sqsService) {
        this.userDao = userDao;
        this.authorityDao = authorityDao;
        this.sqsService = sqsService;
        this.encoder = new BCryptPasswordEncoder();
    }

    private String generateUserRegistrationInfo(User user) throws JsonProcessingException {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", user.getUsername());
        requestBody.put("avatar", user.getAvatarUrl());

        Map<String, Object> message = new HashMap<>();
        message.put("subject", "Registration Confirmation");
        message.put("from", "test@example.com");
        message.put("to_emails", Arrays.asList(user.getEmail()));
        message.put("to_usernames", Arrays.asList(user.getUsername()));
        message.put("request", requestBody);

        String messageJson = new ObjectMapper().writeValueAsString(message);
        return messageJson;
    }

    @Transactional(rollbackFor = {JsonProcessingException.class})
    public User createUser(User user) throws JsonProcessingException {
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDao.save(user);
        addAuthorityRolesToUser(user, AuthorityRole.REGISTERED_USER);
        String registrationConfirmation = generateUserRegistrationInfo(user);
        sqsService.sendMessage(registrationConfirmation);
        return user;
    }

//    @Transactional
//    public void random() {
//        createUser(new User());
//        getAll();
//    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userDao.findAll();
    }

    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userDao.findById(id);
    }

    @Transactional(readOnly = true)
    public User getByIdAlongWithEvents(Long id) {
        return userDao.findByIdAlongWithEvents(id);
    }

    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<User> getByFirstNameIgnoreCase(String firstName) {
        return userDao.findByFirstNameIgnoreCase(firstName);
    }

    @Transactional(readOnly = true)
    public List<User> getByLastNameIgnoreCase(String lastName) {
        return userDao.findByLastNameIgnoreCase(lastName);
    }

    @Transactional(readOnly = true)
    public User getByUsernameOrEmail(String usernameOrEmail) throws NotFoundException, NullPointerException {
        if (usernameOrEmail == null || usernameOrEmail.equals("")) {
            throw new NullPointerException("Null username or email");
        }

        User user = userDao.findByUsername(usernameOrEmail);
        if (user == null) {
            user = userDao.findByEmailIgnoreCase(usernameOrEmail);
        }
        if (user == null) {
            throw new NotFoundException("No such User with username or email: " + usernameOrEmail);
        }

        return user;
    }

    @Transactional
    public void addAuthorityRolesToUser(User user, String... authorityRoles) {

        for (String authorityRole : authorityRoles) {
            authorityDao.save(new Authority(user, authorityRole));
        }
    }

    @Transactional(readOnly = true)
    public List<Authority> getUserAuthorities(User user) {
        return authorityDao.findByUserId(user.getId());
    }
}
