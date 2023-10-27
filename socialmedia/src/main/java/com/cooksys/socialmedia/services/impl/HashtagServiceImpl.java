package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dto.HashtagResponseDto;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.dto.HashtagDto;
import com.cooksys.socialmedia.dto.TweetResponseDto;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.HashtagMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Comparator;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;
  
    private final TweetRepository tweetRepository;
  
    private final HashtagMapper hashtagMapper;

    public List<HashtagResponseDto> getAllHashtags() {
        return hashtagMapper.hashtagEntitiesToResponseDtos(hashtagRepository.findAll());
    }

    @Override
    public List<TweetResponseDto> getTweetsByHashtag(String label) {
        if (label.startsWith("#")) {
            label = label.substring(1);
        }

        List<Tweet> tweets = tweetRepository.findAllByDeletedFalse();
        List<Tweet> taggedTweets = new ArrayList<>();

        Pattern pattern = Pattern.compile("#\\w+");
        for (Tweet tweet : tweets) {
            if (tweet.getContent() != null) {
                Matcher matcher = pattern.matcher(tweet.getContent());

                while (matcher.find()) {
                    String hashtag = matcher.group();

                    if (hashtag.substring(1).equals(label)) {
                        taggedTweets.add(tweet);
                        break;
                    }
                }
            }
        }

        taggedTweets.sort(Comparator.comparing(Tweet::getPosted).reversed());

        if (taggedTweets.isEmpty()) {
            throw new NotFoundException("No tweets with the hashtag #" + label + " were found");
        }

        List<TweetResponseDto> tweetResponseDtos = new ArrayList<>();
        for (Tweet tweet : taggedTweets) {
            TweetResponseDto tweetResponseDto = hashtagMapper.entityToTweetResponseDto(tweet);
            tweetResponseDtos.add(tweetResponseDto);
        }

        return tweetResponseDtos;
    }

}

