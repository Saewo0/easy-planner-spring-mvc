package com.savvy.api.v1;

import com.savvy.service.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;


@Controller
@RequestMapping(value = "/api/photo")
@ResponseBody
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void uploadPhoto(@RequestParam(name = "pic") MultipartFile photo, Principal principal) {
        logger.debug(photo.getOriginalFilename());
        try {
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            String username = (String) auth.getPrincipal();
            String username = principal.getName();
            photoService.savePhoto(photo, username);
        } catch (Exception e) {

        }

        return;
    }
}
