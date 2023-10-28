package com.cooksys.socialmedia.services;

public interface ValidateService {

	public boolean isUsernameAvailable(String username);
	
	public boolean tagExists(String label);

	public boolean usernameExists(String username);

}

