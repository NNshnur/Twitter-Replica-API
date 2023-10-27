package com.cooksys.socialmedia.controllers;

import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.dto.UserResponseDto;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.services.HashtagService;
import com.cooksys.socialmedia.services.TweetService;
import com.cooksys.socialmedia.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/tweets")
public class TweetController {

    private final TweetService tweetService;
    private final UserService userService;

    private final HashtagService hashtagService;

    @GetMapping("/{id}")
    public TweetResponseDto getTweetById(@PathVariable Long id) {
        return tweetService.findById(id);
    }

//    @GetMapping("/entity/{id}")
//    public Optional<Tweet> findTweetById(@PathVariable Long id) {
//        return tweetService.findTweetById(id);
//    }

    @GetMapping("/{id}/likes")
    public List<UserResponseDto> findAllUsersWhoLikedTweet(@PathVariable Long id) {
        return tweetService.getAllUserLikesOfTweet(id);
    }

    @GetMapping("/{id}/tags")
    public List<Hashtag> findAllTagsForTweet(@PathVariable Long id) {
        return tweetService.getAllTagsFromTweet(id);
    }

}
