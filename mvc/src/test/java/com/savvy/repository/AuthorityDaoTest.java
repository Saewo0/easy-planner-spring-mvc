package com.savvy.repository;

import com.savvy.config.AppConfig;
import com.savvy.domain.Authority;
import com.savvy.domain.AuthorityRole;
import com.savvy.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;


@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("unit")
public class AuthorityDaoTest {
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
