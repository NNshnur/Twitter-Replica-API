package com.cooksys.socialmedia.controllers;

import com.cooksys.socialmedia.dto.HashtagResponseDto;
import com.cooksys.socialmedia.services.HashtagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {
    private final HashtagService hashtagService;

    @GetMapping
    public List<HashtagResponseDto> getAllHashtags() {
        return hashtagService.getAllHashtags();
    }
}
