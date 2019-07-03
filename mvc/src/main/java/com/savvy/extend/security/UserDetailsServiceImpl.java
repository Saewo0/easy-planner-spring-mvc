package com.savvy.extend.security;

import com.savvy.domain.Authority;
import com.savvy.domain.User;
import com.savvy.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String keyWord) {
        User domainUser = null;
        try {
            domainUser = userService.getByUsernameOrEmail(keyWord);
        } catch (NotFoundException | NullPointerException e) {
            e.printStackTrace();
        }

        List<Authority> authorities = userService.getUserAuthorities(domainUser);
        domainUser.setAuthorities(authorities);

        return domainUser;
    }
}
