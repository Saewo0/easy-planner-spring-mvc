package com.savvy.repository;

import com.savvy.config.AppConfig;
import com.savvy.domain.Event;
import com.savvy.domain.User;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("unit")
public class UserDaoTest {
    @Autowired
    private UserDao userDao;
    @Autowired
    private EventDao eventDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    @Transactional
    public void findByIdTest() {
        User expectedResult = new User();
        expectedResult.setUsername("SavvyZ");
        expectedResult.setFirstName("Xinwei");
        expectedResult.setLastName("Zhang");
        expectedResult.setEmail("xinwei.z@columbia.edu");
        userDao.save(expectedResult);
        User actualResult = userDao.findById(expectedResult.getId());
        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @Transactional
    public void findByIdEagerTest() {
//        assertTrue(true);
        User expectedResult = new User();
        expectedResult.setUsername("zhang3");
        expectedResult.setPassword("xxx");
        expectedResult.setFirstName("Xinwei");
        expectedResult.setLastName("Zhang");
        expectedResult.setEmail("xinwei.z@columbia.edu");
        userDao.save(expectedResult);

        Event event = new Event();
        event.setDest("AMC Empire 25");
        event.setDestId("daqx1c2135ad");
        event.setEventName("Avengers: End Game");
        event.setHost(expectedResult);
        event.setStartDateTime(OffsetDateTime.now());
        event.setEndDateTime(OffsetDateTime.now());
        eventDao.save(event);
        assertNotNull(event.getId());
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().refresh(expectedResult);

        User actualResult = userDao.findByIdEager(expectedResult.getId());
        List<Event> events = actualResult.getEvents();

        assertEquals(events.size(), 1);
    }

    @Test
    @Transactional
    public void findByUsernameIgnoreCaseTest() {
        User expectedResult = new User();
        expectedResult.setUsername("real");
        expectedResult.setPassword("realPassword");
        expectedResult.setFirstName("Real");
        expectedResult.setLastName("Test");
        expectedResult.setEmail("realUser@test.com");

        User fakeResult = new User();
        expectedResult.setUsername("fake");
        expectedResult.setPassword("fakePassword");
        expectedResult.setFirstName("Fake");
        expectedResult.setLastName("Test");
        expectedResult.setEmail("fakeUser@test.com");

        userDao.save(expectedResult);
        userDao.save(fakeResult);
        User actualResult = userDao.findByUsername("Real");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    @Transactional
    public void findByFirstNameIgnoreCaseTest() {
        User expectedResult = new User();
        expectedResult.setUsername("real");
        expectedResult.setPassword("xxx");
        expectedResult.setFirstName("Real");
        expectedResult.setLastName("Test");
        expectedResult.setEmail("realUser@test.com");

        userDao.save(expectedResult);
        List<User> actualResultList = userDao.findByFirstNameIgnoreCase(expectedResult.getFirstName());

        assertEquals(expectedResult, actualResultList.get(0));
    }

    @Test
    @Transactional
    public void findByLastNameIgnoreCaseTest() {
        User expectedResult = new User();
        expectedResult.setUsername("real");
        expectedResult.setPassword("xxx");
        expectedResult.setFirstName("Real");
        expectedResult.setLastName("Test");
        expectedResult.setEmail("realUser@test.com");

        userDao.save(expectedResult);
        List<User> actualResultList = userDao.findByLastNameIgnoreCase(expectedResult.getLastName());

        assertEquals(expectedResult, actualResultList.get(0));
    }
}
