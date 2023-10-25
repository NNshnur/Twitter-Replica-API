package com.cooksys.socialmedia.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@NoArgsConstructor
@Data
@Table(name="user_table")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp joined;

    @Column(nullable = false)
    private boolean deleted = false;

    @Embedded
    private Profile profile;

    @Embedded
    private Credentials credentials;

    @OneToMany(mappedBy = "author")
    private List<Tweet> tweets;

    @ManyToMany
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tweet_id")
    )
    private List<Tweet> likedTweets = new ArrayList<>();

    @ManyToMany(mappedBy = "mentionedUsers")
    private List<Tweet> mentionedTweets = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "followers_following")
    private List<User> followers = new ArrayList<>();

    @ManyToMany(mappedBy = "followers")
    private List<User> following = new ArrayList<>();

}
