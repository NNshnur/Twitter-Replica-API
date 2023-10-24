package com.cooksys.socialmedia.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
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

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Timestamp joined;

    @Column(nullable = false)
    private boolean deleted;

    @Embedded
    private Profile profile;

    @Embedded
    private Credentials credentials;

    @OneToMany (mappedBy = "user")
    private List<Integer> followers;

    @OneToMany (mappedBy = "user")
    private List<Integer> followings;

    @OneToMany (mappedBy = "tweet")
    private List<Tweet> tweets;







}
