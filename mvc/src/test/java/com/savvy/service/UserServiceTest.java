package com.savvy.service;

import com.savvy.config.AppConfig;
import com.savvy.domain.Authority;
import com.savvy.domain.AuthorityRole;
import com.savvy.domain.User;
import com.savvy.repository.AuthorityDao;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;


@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("unit")
public class UserServiceTest {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    UserService userService;

    @Autowired
    AuthorityDao authorityDao;

    @Test
    @Transactional
    public void createUserTest() {

        User expectedResult = new User();
        expectedResult.setUsername("expectedUser");
        expectedResult.setPassword("expectedPassword");
        expectedResult.setFirstName("Expected");
        expectedResult.setLastName("Test");
        expectedResult.setEmail("expected@test.com");

        userService.createUser(expectedResult);

        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().refresh(expectedResult);

        User actualResult = userService.getById(expectedResult.getId());

        List<Authority> authorities = authorityDao.findByUserId(actualResult.getId());
        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);
        assertEquals(authorities.size(), 1);
        assertEquals(authorities.get(0).getAuthorityRole(), AuthorityRole.REGISTERED_USER);
    }

}
