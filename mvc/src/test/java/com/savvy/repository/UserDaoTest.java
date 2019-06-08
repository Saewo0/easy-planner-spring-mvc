package com.savvy.repository;

import com.savvy.config.AppConfig;
import com.savvy.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertNotNull;

@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("unit")
public class UserDaoTest {
    @Autowired
    private UserDao userDao = new UserDaoImpl();

    @Test
    @Transactional
    public void findByIdTest() {
//        assertTrue(true);
        User expectedResult = new User();
        expectedResult.setUserName("zhang3");
        expectedResult.setFirstName("Xinwei");
        expectedResult.setLastName("Zhang");
        expectedResult.setEmail("xinwei.z@columbia.edu");
        userDao.save(expectedResult);
        User actualResult = userDao.findById(expectedResult.getId());
        assertNotNull(actualResult);
    }
}
