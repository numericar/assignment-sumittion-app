package com.coderscampus.AssignSubmitApp.configs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.coderscampus.AssignSubmitApp.entities.User;
import com.coderscampus.AssignSubmitApp.repositories.UserRepository;

// ใช้สำหรับโหลดข้อมูลของผู้ใช้จากฐานข้อมูล
public class AppUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public AppUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = this.userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Invalid Credentials");
        } 

        User user = userOptional.get();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));

        return new AppUserDetails(user.getUsername(), user.getPassword(), authorities);
    }

}
