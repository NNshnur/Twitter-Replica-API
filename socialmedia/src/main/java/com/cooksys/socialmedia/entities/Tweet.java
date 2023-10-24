package com.cooksys.socialmedia.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
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

    @Column(nullable = false)
    private Timestamp posted;

    @Column(nullable = false)
    private boolean deleted;

    @Column
    private String content;

    @ManyToOne
    private Tweet inReplyTo;

    @ManyToOne
    private Tweet repostOf;

    @OneToMany (mappedBy = "tweet")
    private List<Integer> userLikes;

    @OneToMany (mappedBy = "tweet")
    private List<Integer> userMentions;




}
