package com.shinhan.user;

import java.util.Scanner;

//Controller(사용자가 요청하면  요청을 처리하고 응답을 보낸다. (Servlet으로 변경할 예정이다)
//Web아니고 Console로 동작하는 프로그램이므로 키보드입력을 이용(System.in)
public class UserController {
	static Scanner sc = new Scanner(System.in);
	static UserService userService = new UserService();

	public void execute() {
	      boolean isStop = false;

	      while(!isStop) {
	    	  if(LoginSession.isLoggedIn()) {
	    	  UserView.LoggenInDisplay();
	    	  String job = sc.nextLine(); 
	    	  switch(job) {
		    	  case "1"-> {f_ShowUserInfo();}
		    	  case "2"-> {f_Logout();}
		    	  case "0"-> {isStop = true;}
		    	  default -> {System.out.println("잘못된 입력입니다. 다시 입력하세요.");}
	    	  }
	      } else {
	    	  UserView.menuDisplay();
	    	  String job = sc.nextLine();
	    	  switch(job) {
		    	  case "1"-> {f_Register();}
		    	  case "2"-> {f_Login(); isStop = true;}
		    	  case "0"-> {isStop = true;}
		    	  default -> {System.out.println("잘못된 입력입니다. 다시 입력하세요.");}
	    	  }
	      }
	     }
	}
	private void f_Logout() {
		LoginSession.setLoginUser(null);
		UserView.display("로그아웃 되었습니다.");
	}
	private void f_ShowUserInfo() {
		UserDTO user = LoginSession.getLoginUser();
		if(user != null) {
			UserView.displayUserInfo(user);
		} else {
			UserView.display("로그인 상태가 아닙니다.");
		}
	}
	private void f_Login() {
		System.out.println("------- 로그인 -------");
		System.out.print("이메일을 입력하세요 >> ");
		String email = sc.nextLine();
		System.out.print("비밀번호를 입력하세요 >> ");
		String password = sc.nextLine();
		
		UserDTO user = userService.LoginUser(email, password);
		if (user != null) {
            LoginSession.setLoginUser(user);
            UserView.display("로그인 성공!");
        } else {
            UserView.display("로그인 실패: 이메일 또는 비밀번호가 틀렸습니다.");
        }
	}
	private void f_Register() {
		System.out.println("------- 회원 가입 -------");
		System.out.print("이메일을 입력하세요 >> ");
		String email = sc.nextLine();
		System.out.print("비밀번호를 입력하세요 >> ");
		String password = sc.nextLine();
		System.out.print("권한을 입력하세요(admin, student) >> ");
		String role = sc.nextLine();
		
		UserDTO user = new UserDTO();
		user.setEmail(email);
		user.setPassword(password);
		user.setRole(role);
		
		UserView.display(userService.RegisterUser(user));
	}
}






