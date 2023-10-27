package com.cooksys.socialmedia.services;

public interface ValidateService {

  boolean isUsernameAvailable(String username);
	
	boolean tagExists(String label);

	public boolean usernameExists(String username);

}

