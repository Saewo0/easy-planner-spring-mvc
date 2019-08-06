package com.savvy.repository;

import com.savvy.domain.Event;
import com.savvy.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class EventDaoTest extends DaoTest {
    @Autowired
    private UserDao userDao;

    @Autowired
    private EventDao eventDao;

    @Test
    @Transactional
    public void findByIdTest() {
        User tmpUser = new User();
        tmpUser.setUsername("zhang3");
        tmpUser.setPassword("xxx");
        tmpUser.setFirstName("Xinwei");
        tmpUser.setLastName("Zhang");
        tmpUser.setEmail("xinwei.z@columbia.edu");
        userDao.save(tmpUser);
        Event expectedResult = new Event();
        expectedResult.setDest("AMC Empire 25");
        expectedResult.setDestId("daqx1c2135ad");
        expectedResult.setEventName("Avengers: End Game");
        expectedResult.setHost(tmpUser);
        expectedResult.setStartDateTime(OffsetDateTime.now());
        expectedResult.setEndDateTime(OffsetDateTime.now());
        eventDao.save(expectedResult);
        Event actualResult = eventDao.findById(expectedResult.getId());
        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);
    }
}
