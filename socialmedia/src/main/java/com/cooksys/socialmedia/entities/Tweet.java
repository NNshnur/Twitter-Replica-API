package com.cooksys.socialmedia.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Tweet {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User author;

   @CreationTimestamp
    private Timestamp posted;


    private boolean deleted = false;

    private String content;

    @ManyToOne
    private Tweet inReplyTo;

    @OneToMany(mappedBy = "inReplyTo")
    private List<Tweet> replies = new ArrayList<>();
    @ManyToOne
    private Tweet repostOf;

    @OneToMany (mappedBy = "repostOf")
    private List<Tweet> reposts = new ArrayList<>();

    @OneToMany (mappedBy = "likedTweets")
    private List<User> likedByUsers = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_mentions",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> mentionedUsers = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "tweet_hashtags",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private List<Hashtag> hashtags = new ArrayList<>();




}
