package com.cooksys.socialmedia.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BadRequestException extends RuntimeException {

    static final long serialVersionUID = -7011197190745754945L;

    private String message;
}