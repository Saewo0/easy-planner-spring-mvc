package com.savvy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.savvy.domain.Authority;
import com.savvy.domain.AuthorityRole;
import com.savvy.domain.User;
import com.savvy.repository.AuthorityDao;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;


public class UserServiceTest extends ServiceTest{

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    UserService userService;

    @Autowired
    AuthorityDao authorityDao;

    @Test
    @Transactional
    public void createUserTest() throws JsonProcessingException {

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
