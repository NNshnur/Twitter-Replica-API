package com.cooksys.socialmedia.services.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.socialmedia.dto.CredentialsDto;
import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.dto.UserRequestDto;
import com.cooksys.socialmedia.dto.UserResponseDto;
import com.cooksys.socialmedia.entities.Credentials;
import com.cooksys.socialmedia.entities.Profile;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.BadRequestException;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
    private final UserRepository userRepository;

	private final UserMapper userMapper;
	
	private final TweetMapper tweetMapper;
  
    private final TweetRepository tweetRepository;
  
	private void validateUserRequest(UserRequestDto userRequestDto) {
		if (userRequestDto.getProfile() == null || userRequestDto.getCredentials() == null) {
			throw new BadRequestException("User must provide email, username, and password!");
		}

		if (userRequestDto.getCredentials().getPassword() == null || userRequestDto.getProfile().getEmail() == null
				|| userRequestDto.getCredentials().getUsername() == null) {
			throw new BadRequestException("User must provide email, username, and password!");
		}
	}
	
	// returns an error if username is taken
	// otherwise returns "available" or "deleted"
	private String checkUsernameStatus(String username) {
		Optional<User> optionalUser = userRepository.findByCredentials_Username(username);
		if (optionalUser.isPresent() && optionalUser.get().isDeleted() == false) {
			throw new BadRequestException("Username is taken! Please choose another username.");
		}

		if (optionalUser.isPresent() && optionalUser.get().isDeleted()) {
			return "deleted";
		}
		// if it passes the 2 checks above, it's an available username
		return "availabe";
	}
	
	// returns user even if it has been deleted
	private User getUser(String username) {
		// able to grab users even if they have been deleted
		Optional<User> optionalUser = userRepository.findByCredentials_Username(username);
		if (optionalUser.isEmpty()) {
			throw new NotFoundException("User not found.");
		}
		return optionalUser.get();
	}
	
	private User activeUser(String username) {
		User user = getUser(username);
		if (user.isDeleted()) {
			throw new NotFoundException("User not found!");
		}
		return user;
	}
		
	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		validateUserRequest(userRequestDto);
		String username = userRequestDto.getCredentials().getUsername();
		String usernameStatus = checkUsernameStatus(username);

		if (usernameStatus.equals("deleted")) {
			User deletedUser = getUser(username);
//			User deletedUser = getUser(userRequestDto.getCredentials().getUsername());
			deletedUser.setDeleted(false);
			return userMapper.entityToResponseDto(userRepository.saveAndFlush(deletedUser));
		}
		return userMapper.entityToResponseDto(userRepository.saveAndFlush(userMapper.dtoToEntity(userRequestDto)));
	}

	@Override
	public List<UserResponseDto> getUserFollowers(String username) {
		User user = activeUser(username);
		// go through repository
		List<User> followers = user.getFollowers();
		return userMapper.entitiesToResponseDtos(followers);
	}

	@Override
	public List<UserResponseDto> getUserFollowing(String username) {
		User user = activeUser(username);
		List<User> following = user.getFollowing();
		return userMapper.entitiesToResponseDtos(following);
	}

	@Override
	public List<TweetResponseDto> getUserMentions(String username) {
		User user = activeUser(username);
		List<Tweet> tweets = user.getMentionedTweets();
		return tweetMapper.entitiesToResponseDtos(tweets);
	}



    private User validateCredentials(CredentialsDto credentialsDto) {
        if (credentialsDto.equals(null)) {
            throw new NotAuthorizedException("Not authorized user");
        }
        User user = userRepository.findByCredentialsUsernameAndDeletedFalse(credentialsDto.getUsername());
        if (user != null && user.getCredentials().getPassword().equals(credentialsDto.getPassword())) {
            return user;
        } else {
            throw new BadRequestException("The password provided is incorrect");
        }
    }


//    public boolean usernameExists(String username) {
//        List<User> allUsers = userRepository.findAll();
//        for (User u : allUsers) {
//            if (u.getCredentials().getUsername().equals(username)) {
//                return true;
//            }
//        }
//        return false;
//    }

    public List<TweetResponseDto> getAllTweetsByUser(String username) {
        User userToFind = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (userToFind == null) {
            throw new NotFoundException("User not found");
        }
        List<Tweet> allTweetsByUser = userToFind.getTweets();
        for (Tweet t : allTweetsByUser) {
            if (t.isDeleted()) {
                allTweetsByUser.remove(t);
            }
            if (!(t.getAuthor().equals(userToFind))) {
                allTweetsByUser.remove(t);
            }

        }
        return tweetMapper.entitiesToResponseDtos(allTweetsByUser);
    }

    public UserResponseDto deleteUser(CredentialsDto credentialsDto) {
        User userToDelete = validateCredentials(credentialsDto);
        if (userToDelete.isDeleted() || userToDelete == null) {
            throw new NotFoundException("user doesn't exist");
        }
        userToDelete.setDeleted(true);

        return userMapper.entityToResponseDto(userRepository.saveAndFlush(userToDelete));

    }

    public UserResponseDto updateUserProfile(UserRequestDto userRequestDto, String username) {
            if (userRequestDto.getCredentials() == null) {
            	throw new NotFoundException("Credentials empty");
             }
            User userToUpdate =  validateCredentials(userRequestDto.getCredentials());
            if (userToUpdate.isDeleted() || userToUpdate == null  || userRequestDto.getProfile() == null ) {
                throw new NotFoundException("The user is either deleted or does not exist in DB");
            }

            userToUpdate = userRepository.findByCredentialsUsernameAndDeletedFalse(username);

            Profile profileUpdate = new Profile();
            if (userRequestDto.getProfile().getEmail() != null) {
            	profileUpdate.setEmail(userRequestDto.getProfile().getEmail());
            } else {
            	profileUpdate.setEmail(getUser(userRequestDto.getCredentials().getUsername()).getProfile().getEmail());
            }
            if (userRequestDto.getProfile().getFirstName() != null) {
            	profileUpdate.setFirstName(userRequestDto.getProfile().getFirstName());
            } else {
            	profileUpdate.setFirstName(getUser(userRequestDto.getCredentials().getUsername()).getProfile().getFirstName());
            }
            if (userRequestDto.getProfile().getLastName() != null) {
            	profileUpdate.setLastName(userRequestDto.getProfile().getLastName());
            } else {
            	profileUpdate.setLastName(getUser(userRequestDto.getCredentials().getUsername()).getProfile().getLastName());
            }
            if (userRequestDto.getProfile().getPhone() != null) {
            	profileUpdate.setPhone(userRequestDto.getProfile().getPhone());
            } else {
            	profileUpdate.setPhone(getUser(userRequestDto.getCredentials().getUsername()).getProfile().getPhone());
            }

            userToUpdate.setProfile(profileUpdate);
            Credentials credentialsUpdate = new Credentials();
            credentialsUpdate.setUsername(username);
            credentialsUpdate.setPassword(userRequestDto.getCredentials().getPassword());
            userToUpdate.setCredentials(credentialsUpdate);
            userRepository.saveAndFlush(userToUpdate);

            return userMapper.entityToResponseDto(userToUpdate);
        }

    @Override
    public List<UserResponseDto> getAllUsers() {
      List<User> allUsers = userRepository.findByDeletedFalse();
        if (allUsers.isEmpty()) {
            return new ArrayList<>();
        }
        return userMapper.entitiesToResponseDtos(allUsers);
    }

    @Override
    public UserResponseDto followUser(CredentialsDto credentialsDto,String username) {
        User user = validateCredentials(credentialsDto);
        if(user == null) {
            throw new NotAuthorizedException("Invalid user");
        }

        User userToFollow = userRepository.findByCredentialsUsernameAndDeletedFalse(username);

        if(userToFollow == null) {
            throw new NotFoundException("User to follow was not found");
        }
        if (user.getFollowing().contains(userToFollow)) {
            throw new BadRequestException("User is already following that user");
        }

        user.getFollowing().add(userToFollow);
        userToFollow.getFollowers().add(user);
        userRepository.saveAndFlush(user);

        return userMapper.entityToResponseDto(userToFollow);
    }

    @Override
    public UserResponseDto unFollowUser(CredentialsDto credentialsDto, String username) {
        User user = validateCredentials(credentialsDto);

        if (user == null) {
            throw new NotAuthorizedException("User is invalid");
        }

        User userToUnfollow = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if(userToUnfollow == null) {
            throw new NotFoundException("User to unfollow was not found");
        }
        if(!user.getFollowing().contains(userToUnfollow)) {
            throw new BadRequestException("User is not following that user");
        }

        user.getFollowing().remove(userToUnfollow);
        userToUnfollow.getFollowers().remove(user);
        userRepository.saveAndFlush(user);

        return userMapper.entityToResponseDto(userToUnfollow);
        }

    @Override
    public List<TweetResponseDto> getUserFeed(String username) {
        User user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (user == null) {
            throw new NotFoundException("User not found with username " + username);
        }
        List<Tweet> userTweets = tweetRepository.findByAuthorAndDeletedFalse(user);

        List<Tweet> followingTweets = new ArrayList<>();
        for (User followingUser : user.getFollowing()) {
            List<Tweet> tweetsByFollowingUser = tweetRepository.findByAuthorAndDeletedIsFalseAndContentIsNotNull(followingUser);
            followingTweets.addAll(tweetsByFollowingUser);
        }

        List<Tweet> allTweets = new ArrayList<>();
        allTweets.addAll(userTweets);
        allTweets.addAll(followingTweets);

        allTweets.sort((tweet1, tweet2) -> tweet2.getPosted().compareTo(tweet1.getPosted()));

        List<TweetResponseDto> tweetDtos = tweetMapper.entitiesToResponseDtos(allTweets);

        return tweetDtos;
    }

    @Override
    public UserResponseDto getByUsername(String username) {
        User user = getUser(username);
        if (user.isDeleted()) {
            throw new NotFoundException("The user with username was not found");
        }
        return userMapper.entityToResponseDto(user);
    }
}

