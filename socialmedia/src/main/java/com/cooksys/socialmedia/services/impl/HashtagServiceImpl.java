package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dto.HashtagResponseDto;
import com.cooksys.socialmedia.mappers.HashtagMapper;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;

    private final HashtagMapper hashtagMapper;

    public List<HashtagResponseDto> getAllHashtags() {
        return hashtagMapper.hashtagEntitiesToResponseDtos(hashtagRepository.findAll());
    }


}
