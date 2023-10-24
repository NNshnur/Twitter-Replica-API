package com.cooksys.socialmedia.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Credentials {

    @Column(nullable = false)
    private String username;

    @Column (nullable = false)
    private String password;
}
