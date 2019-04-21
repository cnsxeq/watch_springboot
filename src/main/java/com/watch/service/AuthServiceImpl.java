package com.watch.service;

import com.watch.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl {
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;

    private UserDAO userDAO;

    @Autowired
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            UserDAO userDAO) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userDAO = userDAO;
    }

    public UserDetails getLoginToken(String email,String password){
        UsernamePasswordAuthenticationToken upToken=new UsernamePasswordAuthenticationToken(email,password);
        System.out.println(upToken.toString());
        Authentication authentication =authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails=userDetailsService.loadUserByUsername(email);
        return userDetails;
    }

}