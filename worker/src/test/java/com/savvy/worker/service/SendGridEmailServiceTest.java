package com.savvy.worker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.savvy.worker.config.MockConfig;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;


@RunWith(SpringRunner.class)
@SpringBootTest
@Import(MockConfig.class)
@ActiveProfiles("unit")
public class SendGridEmailServiceTest {

    @Autowired
    SendGrid sg;

    @Autowired
    SendGridEmailService sendGridEmailService;

    @Test
    public void sendEmailTest() throws IOException {
        Map<String, String> fakeRequest = new HashMap<>();
        fakeRequest.put("username","Ryo");
        fakeRequest.put("avatar","https://easy-planner.s3.amazonaws.com/Wechat_Icon.jpg");
        fakeRequest.put("attached_text", "Hi, I'm the founder of ASCENDING LLC.");
        fakeRequest.put("request_link", "");

        Map<String, Object> fakeMessage = new HashMap<>();
        fakeMessage.put("subject", "Friend Invitation");
        fakeMessage.put("from", "test@example.com");
        fakeMessage.put("to_emails", Arrays.asList("zhangxwsavvy@gmail.com", "xinwei.z@columbia.edu"));
        fakeMessage.put("to_usernames", Arrays.asList("SavvyZ", "Xinwei"));
        fakeMessage.put("request", fakeRequest);

        String fakeMessageJson = new ObjectMapper().writeValueAsString(fakeMessage);

        Response fakeResponse = Mockito.mock(Response.class);

        Mockito.when(sg.api(any(Request.class))).thenReturn(fakeResponse);

        sendGridEmailService.sendEmail(fakeMessageJson);

        Mockito.verify(sg,  Mockito.times(1)).api(any(Request.class));

    }

}
