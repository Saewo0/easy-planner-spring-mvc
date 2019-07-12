package com.savvy.extend.security;


import com.savvy.config.AppConfig;
import com.savvy.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;


@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("unit")
public class JwtUtilsTest {

    @Autowired
    JwtUtils jwtUtils;

    @Test
    public void jwtUtilsTest() {
        String expectedUsername = "testUser";
        String expectedPassword = "testPassword";

        User user = new User();
        user.setUsername(expectedUsername);
        user.setPassword(expectedPassword);

        String actualToken = jwtUtils.generateToken(user);
        String actualUsername = jwtUtils.getUsernameFromToken(actualToken);

        assertNotNull(actualUsername);
        assertEquals(actualUsername, expectedUsername);
    }
}
