package com.savvy.service;

import com.savvy.domain.Authority;
import com.savvy.domain.AuthorityRole;
import com.savvy.domain.User;
import com.savvy.repository.AuthorityDao;
import com.savvy.repository.UserDao;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    protected UserDao userDao;

    @Autowired
    protected AuthorityDao authorityDao;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Transactional
    public User createUser(User user) {
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDao.save(user);

        addAuthorityRoleToUser(user, AuthorityRole.REGISTERED_USER);

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

    public User findByUsernameOrEmail(String usernameOrEmail) throws NotFoundException, NullPointerException {
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
    public void addAuthorityRoleToUser(User user, String... authorityRoles) {

        for (String authorityRole : authorityRoles) {
            authorityDao.save(new Authority(user, authorityRole));
        }
    }

    @Transactional(readOnly = true)
    public List<Authority> getUserAuthorities(User user) {
        return authorityDao.findByUserId(user.getId());
    }
}
