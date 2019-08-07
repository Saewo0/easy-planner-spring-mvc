package com.savvy.extend.security;

import com.savvy.domain.Authority;
import com.savvy.domain.User;
import com.savvy.service.UserService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String keyWord) throws UsernameNotFoundException {
        User domainUser;
        try {
            domainUser = userService.getByUsernameOrEmail(keyWord);
        } catch (NotFoundException | NullPointerException e) {
            logger.error("can't find user by keyword: " + keyWord, e);
            throw new UsernameNotFoundException("keyword not found");
        }

        List<Authority> authorities = userService.getUserAuthorities(domainUser);
        domainUser.setAuthorities(authorities);

        return domainUser;
    }
}
