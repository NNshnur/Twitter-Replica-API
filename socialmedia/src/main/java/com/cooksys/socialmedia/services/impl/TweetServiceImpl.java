package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dto.TweetRequestDto;
import com.cooksys.socialmedia.dto.UserResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
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

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    private final TweetMapper tweetMapper;
    private final UserMapper userMapper;

    private Tweet getTweet(Long id) {
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if (tweet.isEmpty()) {
            throw new NotFoundException("No tweet with id : " + id);
        }
        return tweet.get();
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

        List<TweetResponseDto> repostDtos = validReposts.stream().map(tweetMapper::entityToResponseDto).collect(Collectors.toList());

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
                .map(userMapper::entityToDto)
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

        TweetResponseDto replyDto = tweetMapper.entityToResponseDto(replyTweet);
        return replyDto;

    }
}

