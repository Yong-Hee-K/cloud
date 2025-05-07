package com.shinhan.user;

public class LoginSession {
	private static UserDTO loginUser;

	public static UserDTO getLoginUser() {
		return loginUser;
	}

	public static void setLoginUser(UserDTO user) {
		loginUser = user;
	}

	public static void clear() {
		loginUser = null;
	}
	
	public static boolean isLoggedIn() {
		return loginUser != null;
	}
}

