package com.savvy.extend.security;

import com.savvy.extend.exception.InvalidTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    private static final String tokenHeader = "JwtToken";
    private static final String bear = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // Step 1: extract token
        String tokenWithBearer = httpServletRequest.getHeader(tokenHeader);
        if (tokenWithBearer != null && tokenWithBearer.startsWith(bear)) {
            String token = tokenWithBearer.substring(bear.length());

            // Step 2: verify token
            try {
                String username = jwtUtils.getUsernameFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    // Substep: our own logic
                // Step 3: authenticated
                UsernamePasswordAuthenticationToken fullyAuthentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(fullyAuthentication);
            } catch (InvalidTokenException | UsernameNotFoundException e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.error("No token found in the request");
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
