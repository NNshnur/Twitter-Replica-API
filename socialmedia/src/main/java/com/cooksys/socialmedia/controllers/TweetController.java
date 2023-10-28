package com.cooksys.socialmedia.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.socialmedia.dto.ContextDto;
import com.cooksys.socialmedia.dto.CredentialsDto;
import com.cooksys.socialmedia.dto.HashtagResponseDto;
import com.cooksys.socialmedia.dto.TweetRequestDto;
import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.dto.UserResponseDto;
import com.cooksys.socialmedia.services.HashtagService;
import com.cooksys.socialmedia.services.TweetService;
import com.cooksys.socialmedia.services.UserService;

import lombok.RequiredArgsConstructor;


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

    @GetMapping("/{id}/likes")
    public List<UserResponseDto> findAllUsersWhoLikedTweet(@PathVariable Long id) {
        return tweetService.getAllUserLikesOfTweet(id);
    }

    @GetMapping("/{id}/tags")
    public List<HashtagResponseDto> findAllTagsForTweet(@PathVariable Long id) {
        return tweetService.getAllTagsFromTweet(id);
    }

    @GetMapping("/{id}/replies")
    public List<TweetResponseDto> findAllRepliesForTweet(@PathVariable Long id) {
        return tweetService.getAllRepliesFromTweet(id);
    }

    @GetMapping("/{id}/context")
    public ContextDto getContextForTweet(@PathVariable Long id) {
           return tweetService.getContextFromTweet(id);
    }

    @PostMapping("/{id}/repost")
    public TweetResponseDto repostTweet(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
        return tweetService.createRepost(id, credentialsDto);
    }

    @GetMapping("/{id}/reposts")
    public List<TweetResponseDto> getRepostsOfTweet(@PathVariable Long id) {
        return tweetService.getRepostsOfTweet(id);
    }

    @GetMapping("/{id}/mentions")
    public List<UserResponseDto> getUsersMentioned(@PathVariable Long id) {
        return tweetService.getUsersMentioned(id);
    }

    @PostMapping("/{id}/reply")
    public TweetResponseDto createTweetReply(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto) {
        return tweetService.createTweetReply(id, tweetRequestDto);
    }
    @GetMapping
    public List<TweetResponseDto> getAllTweets() {
        return tweetService.getAllTweets();
    };

    @PostMapping
    public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
        return tweetService.createTweet(tweetRequestDto);
    }

    @DeleteMapping("/{id}")
    public TweetResponseDto deleteTweet(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
        return tweetService.deleteTweet(id, credentialsDto);
    }

    @PostMapping("/{id}/like")
    public void likeTweet(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
        tweetService.likeTweet(id, credentialsDto);
    }

}
