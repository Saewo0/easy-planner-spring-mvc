package com.savvy.api.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


@RestController("UtilRestV2")
@RequestMapping(value = "/api/misc")
public class MiscController {


    private final Logger logger = LoggerFactory.getLogger(getClass());


    @RequestMapping(value = "/picture", method = RequestMethod.POST)
    public Map<String, String> uploadPicture(@RequestParam(name = "pic") MultipartFile picture) {
        logger.debug(picture.getOriginalFilename());
        return new HashMap<>();
    }

//    @RequestMapping(value = "/email", method = RequestMethod.POST)
//    public void
}
