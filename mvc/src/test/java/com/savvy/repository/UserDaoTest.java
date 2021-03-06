package com.savvy.repository;

import com.savvy.domain.Event;
import com.savvy.domain.User;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class UserDaoTest extends DaoTest {
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
        expectedResult.setUsername("expectedUser");
        expectedResult.setPassword("expectedPassword");
        expectedResult.setFirstName("Expected");
        expectedResult.setLastName("Test");
        expectedResult.setEmail("expected@test.com");
        userDao.save(expectedResult);
        User actualResult = userDao.findById(expectedResult.getId());
        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @Transactional
    public void findByIdEagerTest() {
        User expectedResult = new User();
        expectedResult.setUsername("expectedUser");
        expectedResult.setPassword("expectedPassword");
        expectedResult.setFirstName("Expected");
        expectedResult.setLastName("Test");
        expectedResult.setEmail("expected@test.com");
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

        User actualResult = userDao.findByIdAlongWithEvents(expectedResult.getId());
        List<Event> events = actualResult.getEvents();

        assertEquals(events.size(), 1);
    }

    @Test
    @Transactional
    public void findByUsernameTest() {
        User expectedResult = new User();
        expectedResult.setUsername("real");
        expectedResult.setPassword("realPassword");
        expectedResult.setFirstName("Real");
        expectedResult.setLastName("Test");
        expectedResult.setEmail("realUser@test.com");

        User fakeResult = new User();
        fakeResult.setUsername("fake");
        fakeResult.setPassword("fakePassword");
        fakeResult.setFirstName("Fake");
        fakeResult.setLastName("Test");
        fakeResult.setEmail("fakeUser@test.com");

        userDao.save(expectedResult);
        userDao.save(fakeResult);
        User actualResult = userDao.findByUsername("real");

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
