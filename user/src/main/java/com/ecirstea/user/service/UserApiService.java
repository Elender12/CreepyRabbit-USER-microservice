package com.ecirstea.user.service;


import com.ecirstea.user.model.User;

import java.util.List;
import java.util.UUID;

public interface UserApiService {

   List<User> findAll();

   User save(User user);

   User edit(User user);

   User findById(UUID id);

   User delete(UUID id);

   User findByUsername(String username);
}
