package com.savvy.api.v1;

import com.savvy.service.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/api/photo")
@ResponseBody
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // /user/{user_id}
// api/photo POST


}
