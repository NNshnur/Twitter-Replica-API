package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dto.*;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.HashtagMapper;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.dto.TweetRequestDto;
import com.cooksys.socialmedia.dto.UserResponseDto;
import org.springframework.stereotype.Service;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.TweetService;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.sql.Timestamp;



@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
  
    private final UserRepository userRepository;

    private final TweetMapper tweetMapper;

    private final UserMapper userMapper;

    private final HashtagRepository hashtagRepository;

    private final HashtagMapper hashtagMapper;


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
        List<Hashtag> allTags = hashtagRepository.findAll();
        List<HashtagResponseDto> tagsOfTweet = new ArrayList<>();
        for (Hashtag h : allTags) {
            if (h.getTweets().contains(tweet)) {
                tagsOfTweet.add(hashtagMapper.hashtagEntityToResponseDto(h));
            }
        }
        return tagsOfTweet;
    }

    public List<TweetResponseDto> getAllRepliesFromTweet(Long id) {
        Tweet tweet = getTweet(id);
        if (tweet.isDeleted()) {
            throw new NotFoundException("Tweet was deleted");
        }
        List<Tweet> allTweets = tweetRepository.findAll();
        List<TweetResponseDto> allReplies = new ArrayList<>();
        for (Tweet t : allTweets) {
            if (t.getReplies().contains(tweet) && (t.isDeleted() == false)) {
                allReplies.add(tweetMapper.tweetEntityToResponseDto(t));
            }
        }
        return allReplies;
    }
    public ContextDto getContextFromTweet(Long id) {
        // might have misunderstood the ask here
        Tweet tweet = getTweet(id);
        if (tweet.isDeleted()) {
            throw new NotFoundException("Tweet was deleted");
        }
        ContextDto tweetContext = new ContextDto();
        List<TweetResponseDto> beforeList = new ArrayList<>();
        List<TweetResponseDto> afterList = new ArrayList<>();
        Timestamp targetTimestamp = tweet.getPosted();
        List<Tweet> allTweets = tweetRepository.findAll();
        for (Tweet t : allTweets) {
            Timestamp timestamp = t.getPosted();
            if (timestamp.compareTo(targetTimestamp) < 0 && (t.isDeleted() == false)) {
                beforeList.add(tweetMapper.tweetEntityToResponseDto(t));
            }
            if (timestamp.compareTo(targetTimestamp) > 0 && (t.isDeleted() == false)) {
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
        Tweet repost = new Tweet();
        repost.setRepostOf(tweetToRepost);
        User author = userRepository.findByCredentialsUsernameAndDeletedFalse(credentialsDto.getUsername());
        repost.setAuthor(author);
        repost.setHashtags(tweetToRepost.getHashtags());
        List<Tweet> reposts = tweetToRepost.getReposts();
        // may need to set more fields
        tweetRepository.saveAndFlush(repost);
        reposts.add(repost);
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
}

