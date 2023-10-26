package com.cooksys.socialmedia.repositories;

import com.cooksys.socialmedia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByDeletedFalse();

    boolean existsByCredentialsUsername(String username);

   // User findByCredentialsUsernameAndPasswordAndDeletedFalse(String username, String password);

    User findByCredentialsUsernameAndDeletedFalse(String username);
}
