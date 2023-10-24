package com.cooksys.socialmedia.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class NotAuthorizedException extends RuntimeException {

    static final long serialVersionUID = -7034897190745754945L;

    private String message;
}
