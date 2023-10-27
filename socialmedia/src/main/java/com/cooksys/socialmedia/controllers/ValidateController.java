package com.cooksys.socialmedia.controllers;

import com.cooksys.socialmedia.services.ValidateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")
public class ValidateController {

    private final ValidateService validateService;

  @GetMapping("/username/available/@{username}")
    public boolean isUsernameAvailable(@PathVariable String username) {
        return validateService.isUsernameAvailable(username);
    }
	
  @GetMapping("/tag/exists/{label}")
	public boolean tagExists(@PathVariable String label) {
		return validateService.tagExists(label);
	}

	@GetMapping("/username/exists/@{username}")
	public boolean usernameExists(@PathVariable String username) {
		return validateService.usernameExists(username);
	}
}
