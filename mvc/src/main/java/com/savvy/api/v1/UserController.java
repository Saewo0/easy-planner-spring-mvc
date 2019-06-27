package com.savvy.api.v1;

import com.savvy.domain.User;
import com.savvy.repository.UserDao;
import com.savvy.service.UserService;
import com.savvy.service.jms.MessageSQSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(value = "/api/user")
@ResponseBody
public class UserController {

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @Autowired
    MessageSQSService messageSQSService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUserAll() {
        logger.debug("Retrieve all users...");

        List<User> users = userService.getAll();

        return users;
    }

    @RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
    public User getUserInfo(@PathVariable(name = "user_id") Long userId) {
        logger.debug("retrieve the user info of userId: " + userId);

        User user = userDao.findByIdEager(userId);

//        Map<String, String> userInfo = new HashMap<>();
//        userInfo.put("username", user.getUsername());
//        userInfo.put("firstName", user.getFirstName());
//        userInfo.put("lastName", user.getLastName());
//        userInfo.put("email", user.getEmail());

        return user;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createUser(@RequestBody User user) {
        logger.debug("create the user info of username: " + user.getUsername());

        userService.createUser(user);
    }

    @RequestMapping(method = RequestMethod.GET, params = "username")
    public User getUserByUsername(@RequestParam("username") String username) {
        logger.debug("find user by usernames: " + username);
        return userDao.findByUsername(username);
    }
//    @RequestMapping(value = "/picture", method = RequestMethod.POST)
//    public Map<String, String> uploadPicture(@RequestParam(name = "pic") MultipartFile picture) {
//        logger.debug(picture.getOriginalFilename());
//        return new HashMap<>();
//    }
}
