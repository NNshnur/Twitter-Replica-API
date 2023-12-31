package com.cooksys.socialmedia.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {


    Optional<Tweet> findByIdAndDeletedFalse(Long id);

    List<Tweet> findRepostsByRepostOfId(Long id);

    List<Tweet> findByAuthorAndDeletedFalse(User user);

    List<Tweet> findByAuthorAndDeletedIsFalseAndContentIsNotNull(User followingUser);

    List<Tweet> findAllByDeletedFalse();
    
    List<Tweet> findByHashtags_Label(String label);
}
