package com.cooksys.socialmedia.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProfileDto {
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
}
