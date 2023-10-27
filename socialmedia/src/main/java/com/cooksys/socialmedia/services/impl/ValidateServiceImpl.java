package com.cooksys.socialmedia.services.impl;

import java.util.List;
import java.util.Optional;

import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.NotFoundException;
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

	private final HashtagRepository hashtagRepository;

	private final UserRepository userRepository;

  
  @Override
    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByCredentialsUsername(username);
    }

	@Override
	public boolean tagExists(String label) {
		Optional<Hashtag> optionalHashtag = hashtagRepository.findByLabel(label);
		return optionalHashtag.isPresent();
	}


	public boolean usernameExists(String username) {
		List<User> allUsers = userRepository.findAll();
		for (User u : allUsers) {
			if (u.getCredentials().getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}
}
