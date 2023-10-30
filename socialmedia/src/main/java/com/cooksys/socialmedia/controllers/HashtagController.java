package com.cooksys.socialmedia.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.socialmedia.dto.HashtagResponseDto;
import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.services.HashtagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {
  
    private final HashtagService hashtagService;

    @GetMapping("/{label}")
    public List<TweetResponseDto> getTweetsByHashtag(@PathVariable String label) {
        return  hashtagService.getTweetsByHashtag(label);
    }
  
    @GetMapping
    public List<HashtagResponseDto> getAllHashtags() {
        return hashtagService.getAllHashtags();
    }
}



