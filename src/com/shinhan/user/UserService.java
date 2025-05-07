package com.shinhan.user;

import lombok.extern.java.Log;

//서비스 :비지니스 로직을 수행한다. DB에 가는 업무는 DAO를 호출한다. 
@Log
public class UserService {
	UserDAO userDAO = new UserDAO();

	public String RegisterUser(UserDTO user) { // 사용자 등록
		return userDAO.RegisterUser(user);
	}
	
	public UserDTO LoginUser(String email, String password) {
	    String result = userDAO.LoginUser(email, password);
	    if ("SUCCESS".equals(result)) {
	        return userDAO.selectByEmail(email); // 로그인 성공 시 사용자 정보 반환
	    }
	    return null;
	}
}