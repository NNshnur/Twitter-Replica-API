package com.cooksys.socialmedia.controllers;

import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/tweets")
public class TweetController {

    private final TweetService tweetService;

//    @GetMapping("/{id}")
//    public TweetResponseDto getTweetById(@PathVariable Long id) {
//        return tweetService.findTweetById(id);
//    }
}
