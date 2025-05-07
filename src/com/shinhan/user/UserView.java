package com.shinhan.user;

//View(Web에서 JSP로 변경될 예정이다)
public class UserView {

	public static void LoggenInDisplay() {
		System.out.println("----- 사용자 메뉴 -----");
		System.out.println("1. 내 정보 보기");
		System.out.println("2. 로그아웃");
		System.out.println("0. 나가기");
		System.out.print("작업을 선택해주세요 >> ");
	}
	
	public static void endDisplay(String message) {
		System.out.println("알림:" + message);
	}
	public static void menuDisplay() {
		System.out.println("1. 회원가입");
		System.out.println("2. 로그인");
		System.out.println("0. 나가기");
		System.out.print("작업을 선택해주세요 >> ");
	}
	
	public static void display(String result) {
        System.out.println("=== 처리 결과 ===");
        System.out.println(result);
        System.out.println("=================");
    }
	
	public static void displayUserInfo(UserDTO user) {
		System.out.println("------- 사용자 정보 -------");
		System.out.println("이메일 : " + user.getEmail());
		System.out.println("권한 : " + user.getRole());
		System.out.println("가입 일자 : " + user.getCreated_time());
		System.out.println("------------------------");
	}
	
	public static void returnDisplay(String message) {
		System.out.println("이전 메뉴로 이동합니다.");
	}
}
