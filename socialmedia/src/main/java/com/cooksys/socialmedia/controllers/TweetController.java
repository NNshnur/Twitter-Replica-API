package com.cooksys.socialmedia.controllers;

import com.cooksys.socialmedia.dto.TweetRequestDto;
import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.dto.UserResponseDto;
import com.cooksys.socialmedia.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/tweets")
public class TweetController {

    private final TweetService tweetService;

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


}
