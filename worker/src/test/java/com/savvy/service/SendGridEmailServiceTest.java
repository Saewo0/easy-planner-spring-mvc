package com.savvy.service;

import com.savvy.worker.service.SendGridEmailService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("unit")
public class SendGridEmailServiceTest {

    @Autowired
    SendGridEmailService sendGridEmailService;



}
