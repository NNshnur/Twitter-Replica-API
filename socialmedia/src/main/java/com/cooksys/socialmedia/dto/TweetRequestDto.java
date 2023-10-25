package com.cooksys.socialmedia.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TweetRequestDto {
    private String content;
    private CredentialsDto credentials;

}
