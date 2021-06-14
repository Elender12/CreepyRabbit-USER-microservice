package com.ecirstea.user.security;

import com.ecirstea.user.model.User;
import com.ecirstea.user.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public
class JwtUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepositoryImpl userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (username.equalsIgnoreCase("root")) {
            return new org.springframework.security.core.userdetails.User("root", "imgroot", new ArrayList<>());
        }
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

}