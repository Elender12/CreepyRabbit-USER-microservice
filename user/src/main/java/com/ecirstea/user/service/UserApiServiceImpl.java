package com.ecirstea.user.service;


import com.ecirstea.user.exception.UserException;
import com.ecirstea.user.model.User;
import com.ecirstea.user.model.UserFeedback;
import com.ecirstea.user.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserApiServiceImpl implements UserApiService {

    @Autowired
    private final UserRepositoryImpl userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserApiServiceImpl(UserRepositoryImpl mongoDBRepository) {
        this.userRepository = mongoDBRepository;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>(this.userRepository.findAll());
        System.out.println("Returned all users.");
        return users;
    }

    @Override
    public User save(User user) {
        User searchedUser = this.userRepository.findByUsername(user.getUsername());
        if (user.getId() == null && searchedUser == null) {
            user.makeId();
            user.setCreated(new Date());
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
        } else if (user.getId() == null && searchedUser != null) {
            throw new UserException(HttpStatus.BAD_REQUEST, "Username not available");
        } else {

            User temp = this.findById(user.getId());
            user.setCreated(temp.getCreated());
        }
        System.out.println("Saved user with id: " + user.getId());
        return this.userRepository.save(user);
    }

    @Override
    public User edit(User user) {
        User temp = this.findById(user.getId()); //throws exception if it doesn't exist.
        user.setCreated(temp.getCreated());
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        System.out.println("Edited user with id: " + user.getId());
        return this.userRepository.save(user);
    }

    @Override
    public User findById(UUID id) {
        User user = this.userRepository.findById(id);

        if (user != null) {
            System.out.println("Found user with id: " + user.getId());
            return user;
        } else {
            System.err.println("User not found with id " + id);
            throw new UserException(HttpStatus.NOT_FOUND, "user not found with id " + id);
        }
    }

    @Override
    public User delete(UUID id) {
        User user = this.userRepository.findById(id);
        if (user != null) {
            this.userRepository.deleteById(user.getId());
            System.out.println("Deleted user with id: " + user.getId());
            return user;
        } else {
            System.err.println("User not found with id " + id);
            throw new UserException(HttpStatus.NOT_FOUND, "user not found with id "+id);
        }
    }

    @Override
    public User findByUsername(String username) {
        User user = this.userRepository.findByUsername(username);
        if (user != null) {
            System.out.println("Found user with username: " + user.getUsername());
            return user;
        } else {
            System.err.println("User not found with username " + username);
            throw new UserException(HttpStatus.NOT_FOUND, "user not found with username " + username);
        }
    }

    @Override
    public String receiveUserFeedback(UserFeedback message) {
        System.out.println(message.toString());
        return HttpStatus.OK.getReasonPhrase();
    }
}
