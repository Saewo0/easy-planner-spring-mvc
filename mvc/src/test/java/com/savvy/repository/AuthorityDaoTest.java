package com.savvy.repository;

import com.savvy.domain.Authority;
import com.savvy.domain.AuthorityRole;
import com.savvy.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

public class AuthorityDaoTest extends DaoTest {
    @Autowired
    private AuthorityDao authorityDao;

    @Autowired
    private UserDao userDao;

    @Test
    @Transactional
    public void findByUserIdTest() {

        User user = new User();
        user.setUsername("real");
        user.setPassword("realPassword");
        user.setFirstName("Real");
        user.setLastName("Test");
        user.setEmail("realUser@test.com");

        userDao.save(user);

        Authority expectedResult = new Authority(user, AuthorityRole.REGISTERED_USER);

        authorityDao.save(expectedResult);

        Authority actualResult = authorityDao.findByUserId(user.getId()).get(0);

        assertEquals(actualResult, expectedResult);
    }
}
