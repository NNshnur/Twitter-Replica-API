package com.cooksys.socialmedia.repositories;

import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {


    @Override
    Optional<Tweet> findById(Long aLong);

    List<Tweet> findRepostsByRepostOfId(Long id);

    List<Tweet> findByAuthorAndDeletedFalse(User user);

    List<Tweet> findByAuthorAndDeletedIsFalseAndContentIsNotNull(User followingUser);

    List<Tweet> findAllByDeletedFalse();

}
