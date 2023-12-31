package com.cooksys.socialmedia.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.socialmedia.dto.ContextDto;
import com.cooksys.socialmedia.dto.CredentialsDto;
import com.cooksys.socialmedia.dto.HashtagResponseDto;
import com.cooksys.socialmedia.dto.TweetRequestDto;
import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.dto.UserResponseDto;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.BadRequestException;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.HashtagMapper;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.TweetService;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
  
    private final UserRepository userRepository;

    private final TweetMapper tweetMapper;

    private final UserMapper userMapper;

    private final HashtagRepository hashtagRepository;

    private final HashtagMapper hashtagMapper;

    private Tweet tweetExists(Long id) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(id);
        if (optionalTweet.isEmpty() || optionalTweet == null) {
            throw new NotFoundException("Couldn't find tweet with that ID.");
        }
        return optionalTweet.get();
    }

    private void validateTweetRequest(TweetRequestDto tweetRequestDto) {
        if (tweetRequestDto.getContent() == null || tweetRequestDto.getCredentials() == null) {
            throw new BadRequestException("Tweet must have content and an author");
        }

        if (tweetRequestDto.getCredentials().getUsername() == null || tweetRequestDto.getCredentials().getPassword() == null) {         
        	throw new BadRequestException("Tweet must have an author (username).");
        }
    }

    private boolean activeUser(String username) {
        List<User> activeUsers = userRepository.findAllByDeletedFalse();
        for (User activeUser : activeUsers) {
            if (username.equals(activeUser.getCredentials().getUsername())) {
                return true;
            }
        }
        return false;
    }

    private User getUser(String username) {
        Optional<User> optionalUser = userRepository.findByCredentials_Username(username);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User not found.");
        }
        return optionalUser.get();
    }    
    
    private Tweet getTweet(Long id) {
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if (tweet.isEmpty()) {
            throw new NotFoundException("No tweet with id : " + id);
        }
        return tweet.get();
    }
    
    public TweetResponseDto findById(Long Id) {
        List<Tweet> allTweets = tweetRepository.findAll();
        for (Tweet t : allTweets) {
            if (t.getId() == Id) {
                TweetResponseDto foundTweet = new TweetResponseDto();
                // I did this manually because the mapper wasn't working
                foundTweet.setAuthor(userMapper.entityToResponseDto(t.getAuthor()));
                foundTweet.setId(Id);
                foundTweet.setPosted(t.getPosted());
                foundTweet.setContent(t.getContent());
                foundTweet.setRepostOf(tweetMapper.tweetEntityToResponseDto(t.getRepostOf()));
                foundTweet.setInReplyTo(tweetMapper.tweetEntityToResponseDto(t.getInReplyTo()));
                return foundTweet;
            }
        }
        TweetResponseDto nullTweet = new TweetResponseDto();
        return nullTweet;
    }

    public List<UserResponseDto> getAllUserLikesOfTweet(Long id) {
        Tweet tweet = getTweet(id);
        if (tweet.isDeleted()) {
            throw new NotFoundException("Tweet was deleted");
        }
        List<UserResponseDto> usersWhoLikedTweet = new ArrayList<>();
        List<User> allUsers = userRepository.findAll();
        for (User u : allUsers) {
            if (u.getLikedTweets().contains(tweet) && (u.isDeleted() == false)) {
                usersWhoLikedTweet.add(userMapper.entityToResponseDto(u));
            }
        }

        return usersWhoLikedTweet;
    }

    public List<HashtagResponseDto> getAllTagsFromTweet(Long id) {
        Tweet tweet = getTweet(id);
        if (tweet.isDeleted()) {
            throw new NotFoundException("Tweet was deleted");
        }
//        List<Hashtag> allTags = hashtagRepository.findAll();
//        List<HashtagResponseDto> tagsOfTweet = new ArrayList<>();
//        for (Hashtag h : allTags) {
//            if (h.getTweets().contains(tweet)) {
//                tagsOfTweet.add(hashtagMapper.hashtagEntityToResponseDto(h));
//            }
//        }
        List<Hashtag> allTagsTweet = tweet.getHashtags();
        return hashtagMapper.hashtagEntitiesToResponseDtos(allTagsTweet);
    }

    public List<TweetResponseDto> getAllRepliesFromTweet(Long id) {
        Tweet tweet = getTweet(id);
        if (tweet.isDeleted()) {
            throw new NotFoundException("Tweet was deleted");
        }
        List<Tweet> allReplies = tweet.getReplies();
        return tweetMapper.entitiesToResponseDtos(allReplies);
    }
    
    public ContextDto getContextFromTweet(Long id) {
        Tweet tweet = getTweet(id);
        if (tweet.isDeleted()) {
            throw new NotFoundException("Tweet was deleted");
        }
        
        ContextDto tweetContext = new ContextDto();
        List<TweetResponseDto> beforeList = new ArrayList<>();
        List<TweetResponseDto> afterList = new ArrayList<>();
        //find all tweets in response and reply to given tweet
        
        Tweet isAReply = tweet.getInReplyTo();        
        if (Objects.nonNull(isAReply)) {
        	beforeList.add(tweetMapper.tweetEntityToResponseDto(isAReply));
        }
        
        List<Tweet> allTweets = tweetRepository.findAll();
        for (Tweet t : allTweets) {
        	if (t.getInReplyTo() == null) {
        		continue;
        	}
        	
            if (t.getInReplyTo().equals(tweet)) {
                afterList.add(tweetMapper.tweetEntityToResponseDto(t));
            }
        }
        
        tweetContext.setTarget(tweetMapper.tweetEntityToResponseDto(tweet));
        tweetContext.setAfter(afterList);
        tweetContext.setBefore(beforeList);
        return tweetContext;
    }

    public TweetResponseDto createRepost(Long id, CredentialsDto credentialsDto) {
        Tweet tweetToRepost = getTweet(id);
        
        if (tweetToRepost.isDeleted()) {
        	throw new NotFoundException("Tweet not found");	
        }
        
        Tweet repost = new Tweet();
        repost.setRepostOf(tweetToRepost);
        
        User author = userRepository.findByCredentialsUsernameAndDeletedFalse(credentialsDto.getUsername());
        if (author == null) {
        	throw new NotFoundException("User not found");
        }
        
        List<Hashtag> tweetToRepostHashtags = new ArrayList<>(tweetToRepost.getHashtags());
        
        repost.setAuthor(author);
        repost.setHashtags(tweetToRepostHashtags);
        repost.setRepostOf(tweetToRepost);
        tweetRepository.saveAndFlush(repost);
        
        List<Tweet> reposts = tweetToRepost.getReposts();
        // may need to set more fields
        reposts.add(repost);
        tweetToRepost.setReposts(reposts);        
        tweetRepository.saveAndFlush(tweetToRepost);
        return tweetMapper.tweetEntityToResponseDto(repost);
    }

    private List<User> parseMentionsFromTweet(Tweet tweet) {
        List<User> mentionedUsers = new ArrayList<>();
        String content = tweet.getContent();

        Pattern pattern = Pattern.compile("@(\\w+)");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String username = matcher.group(1); // Extract the username without the '@'
            User mentionedUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);

            if (mentionedUser != null) {
                mentionedUsers.add(mentionedUser);
            }
        }

        return mentionedUsers;
    }

    @Override
    public List<TweetResponseDto> getRepostsOfTweet(Long id) {
        if (getTweet(id).isDeleted()) {
            throw new NotFoundException("The tweet was deleted");
        }

        List<Tweet> reposts = tweetRepository.findRepostsByRepostOfId(id);

        List<Tweet> validReposts = reposts.stream().filter(tweet -> !tweet.isDeleted()).collect(Collectors.toList());

        List<TweetResponseDto> repostDtos = validReposts.stream().map(tweetMapper::tweetEntityToResponseDto).collect(Collectors.toList());

        return repostDtos;
    }

    @Override
    public List<UserResponseDto> getUsersMentioned(Long id) {
        Tweet tweet = getTweet(id);
        if (tweet.isDeleted()) {
            throw new NotFoundException("The Tweet was deleted");
        }

        List<User> mentionedUsers = parseMentionsFromTweet(tweet);
        List<User> validMentionedUsers = mentionedUsers.stream()
                .filter(user -> !user.isDeleted())
                .filter(user -> !user.getTweets().stream().anyMatch(Tweet::isDeleted))
                .collect(Collectors.toList());

        List<UserResponseDto> userDtos = validMentionedUsers.stream()
                .map(userMapper::entityToResponseDto)
                .collect(Collectors.toList());

        return userDtos;
    }

    @Override
    public TweetResponseDto createTweetReply(Long id, TweetRequestDto tweetRequestDto) {
        Tweet tweet = getTweet(id);
        if(tweet.isDeleted()) {
            throw new NotFoundException("Tweet was deleted");
        }
        User author = userRepository.findByCredentialsUsernameAndDeletedFalse(tweetRequestDto.getCredentials().getUsername());
        if(author == null) {
            throw new NotFoundException("User was not found");
        }
        Tweet replyTweet = new Tweet();
        replyTweet.setAuthor(author);
        replyTweet.setContent(tweetRequestDto.getContent());
        replyTweet.setInReplyTo(tweet);

        tweetRepository.saveAndFlush(replyTweet);

        TweetResponseDto replyDto = tweetMapper.tweetEntityToResponseDto(replyTweet);
        return replyDto;

    }

    @Override
    public List<TweetResponseDto> getAllTweets() {
        return tweetMapper.entitiesToResponseDtos(tweetRepository.findAll());
    }

    @Override
    public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
    	// find user and makes sure not deleted
    	// set author
    	
    
        // validate request
        validateTweetRequest(tweetRequestDto);

        // if credentials don't match an active user, send an error
        String authorUsername = tweetRequestDto.getCredentials().getUsername();
        if (activeUser(authorUsername) == false) {
            throw new NotFoundException("Author not found.");
        }

        // convert tweet into entity
        Tweet tweet = tweetMapper.dtoToEntity(tweetRequestDto);

        // get content, parse through it for mentions and hashtags
        String[] splitContent = tweetRequestDto.getContent().split(" ");
        List<User> mentionedUsers = new ArrayList<>();
        List<Hashtag> hashtags = new ArrayList<>();
        for (String word : splitContent) {
            if (word.startsWith("@")) {
                String username = word.substring(1);
                User user = getUser(username);
                // add a mentioned user to our mentionedUsers array
                mentionedUsers.add(user);
                // add tweet that user gets mentioned in to user
                user.getMentionedTweets().add(tweet);
                userRepository.saveAndFlush(user);

            }

            if (word.startsWith("#")) {

            	// take pound sign off set it equal to label
            	String label = word.substring(1);
            	// find hashtag
            	Hashtag hashtag = hashtagRepository.findByLabel(label);
            	// see if it exists
            	if (hashtag == null) {
            		// create a new hashtag and setlabel to label
            		Hashtag newTag = new Hashtag();
            		newTag.setLabel(label);
            		//   add tweets to new tag
            		newTag.setTweets(Collections.singletonList(tweet));
            		hashtagRepository.saveAndFlush(newTag);
            		//   newTweet.setTags
            		hashtags.add(newTag);
            	} else {
            		// if you don't need to create
            		//   tag.setTaggedTweets
            		hashtag.setTweets(Collections.singletonList(tweet));
            		//   ?? newTweet.setTags ??
            		hashtags.add(hashtag);
            		
            		hashtagRepository.saveAndFlush(hashtag);
            		
            	}

            	// saveandflush the tag
            }
        }
        
        tweet.setMentionedUsers(mentionedUsers);
        tweet.setHashtags(hashtags);
        tweet.setAuthor(getUser(authorUsername));

        // need to update lastused timestamp for hashtag
        return tweetMapper.tweetEntityToResponseDto(tweetRepository.saveAndFlush(tweet));
    }

    @Override
    public TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto) {
    	
        Tweet tweet = tweetExists(id);
        if (tweet == null || tweet.isDeleted()) {
            throw new NotFoundException("Tweet was not found in the DB");
        }

        String tweetAuthor = tweet.getAuthor().getCredentials().getUsername();
        String requestAuthor = credentialsDto.getUsername();
        if (!tweetAuthor.equals(requestAuthor)) {
            throw new NotAuthorizedException("You must be the owner of this tweet to delete it.");
        }
        tweet.setDeleted(true);

        return tweetMapper.tweetEntityToResponseDto(tweetRepository.saveAndFlush(tweet));
    }

    @Override
    public void likeTweet(Long id, CredentialsDto credentialsDto) {
        // get tweet, make sure it's not deleted
        Tweet tweet = tweetExists(id);
        if (tweet.isDeleted()) {
            throw new NotFoundException("Tweet not found");
        }

        // make sure credentials belong to an active user
        if (!activeUser(credentialsDto.getUsername())) {
            throw new NotAuthorizedException("Must be an active user to like this tweet");
        }

        User user = getUser(credentialsDto.getUsername());
        user.getLikedTweets().add(tweet);
        userRepository.saveAndFlush(user);
    }

}

