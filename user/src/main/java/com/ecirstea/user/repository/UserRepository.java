package com.ecirstea.user.repository;



import com.ecirstea.user.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository {

   User save(User user);

   void deleteById( UUID id );

   User findById(UUID id );

   List<User> findAll();

   User findByUsername(String username);

}
