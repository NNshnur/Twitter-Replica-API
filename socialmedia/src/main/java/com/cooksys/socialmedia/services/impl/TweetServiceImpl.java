package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dto.HashtagResponseDto;
import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.dto.UserResponseDto;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.HashtagMapper;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;

    private final TweetMapper tweetMapper;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final HashtagRepository hashtagRepository;

    private final HashtagMapper hashtagMapper;

//    public Optional<Tweet> findTweetById(Long Id) {
//        return tweetRepository.findById(Id);
//    }

    private Tweet getTweet(Long id) {
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if (tweet.isEmpty()) {
            throw new NotFoundException("No tweet with id : " + id);
        }
        return tweet.get();
    }
    public TweetResponseDto findById(Long Id) {
        List<Tweet> allTweets = tweetRepository.findAll();
        for (Tweet t : allTweets) {
            if (t.getId() == Id) {
                TweetResponseDto foundTweet = new TweetResponseDto();
                // I did this manually because the mapper wasn't working
                foundTweet.setAuthor(userMapper.entityToResponseDto(t.getAuthor()));
                foundTweet.setId(Id);
                foundTweet.setPosted(t.getPosted());
                foundTweet.setContent(t.getContent());
                foundTweet.setRepostOf(tweetMapper.tweetEntityToResponseDto(t.getRepostOf()));
                foundTweet.setInReplyTo(tweetMapper.tweetEntityToResponseDto(t.getInReplyTo()));
                return foundTweet;
            }
        }
        TweetResponseDto nullTweet = new TweetResponseDto();
        return nullTweet;
    }

    public List<UserResponseDto> getAllUserLikesOfTweet(Long id) {
        Tweet tweet = getTweet(id);
//        if (tweet == null) {
//            return error
//        }
        if (tweet.isDeleted()) {
            throw new NotFoundException("Tweet was deleted");
        }
        List<UserResponseDto> usersWhoLikedTweet = new ArrayList<>();
        List<User> allUsers = userRepository.findAll();
        for (User u : allUsers) {
            if (u.getLikedTweets().contains(tweet) && (u.isDeleted() == false)) {
                usersWhoLikedTweet.add(userMapper.entityToResponseDto(u));
            }
        }

        return usersWhoLikedTweet;


    }

    public List<HashtagResponseDto> getAllTagsFromTweet(Long id) {
        Tweet tweet = getTweet(id);
        List<Hashtag> allTags = hashtagRepository.findAll();
        List<HashtagResponseDto> tagsOfTweet = new ArrayList<>();
        for (Hashtag h : allTags) {
            if (h.getTweets().contains(tweet)) {
                tagsOfTweet.add(hashtagMapper.hashtagEntityToResponseDto(h));
            }
        }
        return tagsOfTweet;
    }

}
