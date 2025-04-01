package com.coderscampus.AssignSubmitApp.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coderscampus.AssignSubmitApp.repositories.UserRepository;

@Component
public class JwtFilter {

    @Autowired
    private UserRepository userRepository;

    

}
