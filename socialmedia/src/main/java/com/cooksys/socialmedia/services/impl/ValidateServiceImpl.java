package com.cooksys.socialmedia.services.impl;


import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.ValidateService;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private final UserRepository userRepository;
  
  	private final HashtagRepository hashtagRepository;
  
  @Override
    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByCredentialsUsername(username);
    }

	@Override
	public boolean tagExists(String label) {
		Optional<Hashtag> optionalHashtag = hashtagRepository.findByLabel("#" + label);
		return optionalHashtag.isPresent();
	}
}
