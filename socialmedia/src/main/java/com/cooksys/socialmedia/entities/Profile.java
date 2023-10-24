package com.cooksys.socialmedia.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Profile {
    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column
    private String phone;
}
