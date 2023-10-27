package com.cooksys.socialmedia.repositories;

import com.cooksys.socialmedia.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.tokens.Token;

import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    Tweet findById(Token.ID tweetId);
}
