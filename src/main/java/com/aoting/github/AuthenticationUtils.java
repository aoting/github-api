package com.aoting.github;

import java.util.Base64;

/**
 * Providing utility functions for authentication to Github
 */
public class AuthenticationUtils {
	/**
	 * Create header for Basic HTTP 'Authorization'
	 * @param username
	 * @param password
	 * @return
	 */
	public static String getBasicAuthentication(String username, String password) {
		return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
	}
}
