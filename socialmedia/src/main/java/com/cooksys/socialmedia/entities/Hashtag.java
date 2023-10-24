package com.cooksys.socialmedia.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Hashtag {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String label;

    @Column(nullable = false)
    private Timestamp firstUsed;

    @Column(nullable = false)
    private Timestamp lastUsed;

    @OneToMany (mappedBy = "hashtag")
    private List<Integer> tweets;

}
