package com.cooksys.socialmedia.controllers;

import com.cooksys.socialmedia.dto.*;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.BadRequestException;
import com.cooksys.socialmedia.services.HashtagService;
import com.cooksys.socialmedia.services.TweetService;
import com.cooksys.socialmedia.services.UserService;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
