package com.cooksys.socialmedia.repositories;

import com.cooksys.socialmedia.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    @Override
    Optional<Tweet> findById(Long aLong);
}
