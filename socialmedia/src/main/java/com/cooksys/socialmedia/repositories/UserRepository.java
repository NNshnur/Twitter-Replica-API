package com.cooksys.socialmedia.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.cooksys.socialmedia.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByCredentialsUsernameAndDeletedFalse(String username);

    Optional<User> findByCredentials_Username(String username);
  
    List<User> findByDeletedFalse();

    boolean existsByCredentialsUsername(String username);


    List<User> findAllByDeletedFalse();
}
