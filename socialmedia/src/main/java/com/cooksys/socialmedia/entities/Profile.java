package com.cooksys.socialmedia.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Data
public class Profile {
    private String firstName;

    private String lastName;

    @Column(nullable = false)
    private String email;

    private String phone;
}
