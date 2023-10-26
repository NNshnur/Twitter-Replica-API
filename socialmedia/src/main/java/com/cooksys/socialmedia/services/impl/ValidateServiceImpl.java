package com.cooksys.socialmedia.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.services.ValidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {
	
	private final HashtagRepository hashtagRepository;
	
	@Override
	public boolean tagExists(String label) {
		//check if that entity exists in hashtag database
		Optional<Hashtag> optionalHashtag = hashtagRepository.findByLabel("#" + label);
		
		return optionalHashtag.isPresent();
	}
}
