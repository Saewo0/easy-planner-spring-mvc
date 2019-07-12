package com.savvy.api.v1;

import com.savvy.domain.User;
import com.savvy.extend.security.JwtUtils;
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

import java.util.Collections;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/api/user")
@ResponseBody
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSQSService messageSQSService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

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

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Map<String, String> printUsernameAndPassword(@RequestBody User user) {
        logger.debug("Print the user info: " + user.getUsername() + " " + user.getPassword());
        UsernamePasswordAuthenticationToken notFullyAuth = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()
        );

        try {
            authenticationManager.authenticate(notFullyAuth);
            try {
                final UserDetails userDetails = userService.getByUsernameOrEmail(user.getUsername());
                final String token = jwtUtils.generateToken(userDetails);
                return Collections.singletonMap("Token", token);
            } catch (NotFoundException nfe) {
                nfe.printStackTrace();
                // return something indicating user not exist
            } catch (NullPointerException npe) {
                npe.printStackTrace();
                // return something indicating EMPTY username/email
                return Collections.singletonMap("Error", "EMPTY username/email");
            }
        } catch (AuthenticationException ae) {
            logger.debug(ae.getMessage());
            // return something indicating invalid username/password
            return Collections.singletonMap("Error", "Invalid Username/Password");
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
