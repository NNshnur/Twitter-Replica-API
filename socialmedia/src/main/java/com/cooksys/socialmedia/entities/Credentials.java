package com.cooksys.socialmedia.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Credentials {

    @Column(nullable = false, unique = true)
    private String username;

    @Column (nullable = false)
    private String password;

    public void setUsername(String tarnished) {
    }

    public void setPassword(java.lang.String password) {
    }
}
