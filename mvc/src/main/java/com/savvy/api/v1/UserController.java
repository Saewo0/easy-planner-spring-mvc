package com.savvy.api.v1;

import com.savvy.domain.User;
import com.savvy.repository.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/api/user")
@ResponseBody
public class UserController {

    @Autowired
    UserDao userDao;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUserAll() {
        logger.debug("retrieve all users...");

        List<User> users = userDao.findAll();

        return users;
    }

    @RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
    public Map<String, String> getUserInfo(@PathVariable(name = "user_id") Long userId) {
        logger.debug("retrieve the user info of userId: " + userId);

        User user = userDao.findById(userId);

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("username", user.getUsername());
        userInfo.put("firstName", user.getFirstName());
        userInfo.put("lastName", user.getLastName());
        userInfo.put("email", user.getEmail());

        return userInfo;
    }



//    @RequestMapping(value = "/picture", method = RequestMethod.POST)
//    public Map<String, String> uploadPicture(@RequestParam(name = "pic") MultipartFile picture) {
//        logger.debug(picture.getOriginalFilename());
//        return new HashMap<>();
//    }
}
