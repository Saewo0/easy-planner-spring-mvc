package com.savvy.api.v1;

import com.savvy.domain.User;
import com.savvy.extend.security.JwtTokenUtils;
import com.savvy.service.UserService;
import com.savvy.service.jms.MessageSQSService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(value = "/api/user")
@ResponseBody
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    MessageSQSService messageSQSService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenUtils jwtTokenUtils;

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

        User user = userService.getByIdAlongWithEvents(userId);

        return user;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String printUsernameAndPassword(@RequestBody User user) {
        logger.debug("Print the user info of username: " + user.getUsername());
        logger.debug("Print the user info of password: " + user.getPassword());

        UsernamePasswordAuthenticationToken notFullyAuth = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()
        );

        try {
            authenticationManager.authenticate(notFullyAuth);
            try {
                final UserDetails userDetails = userService.getByUsernameOrEmail(user.getUsername());
                final String token = jwtTokenUtils.generateToken(userDetails);
                return token;
            } catch (NotFoundException | NullPointerException e) {
                e.printStackTrace();
            }
        } catch (AuthenticationException ae) {
            ae.printStackTrace();
        }

        return null;
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
        User user = null;
        try {
            user = userService.getByUsernameOrEmail(username);
        } catch (NotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
        return user;
    }
//    @RequestMapping(value = "/picture", method = RequestMethod.POST)
//    public Map<String, String> uploadPicture(@RequestParam(name = "pic") MultipartFile picture) {
//        logger.debug(picture.getOriginalFilename());
//        return new HashMap<>();
//    }
}

class TmpUserInfo {
    private String username;
    private String password;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}