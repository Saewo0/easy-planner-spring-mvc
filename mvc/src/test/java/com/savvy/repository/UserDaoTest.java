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

    @Test
    @Transactional
    public void findByIdEagerTest() {
//        assertTrue(true);
        User expectedResult = new User();
        expectedResult.setUserName("zhang3");
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

}
