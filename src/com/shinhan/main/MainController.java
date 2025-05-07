package com.shinhan.main;

import java.util.Scanner;

import com.shinhan.file.FileController;
import com.shinhan.folder.FolderController;
import com.shinhan.report.ReportController;
import com.shinhan.sharedlink.SharedLinkController;
import com.shinhan.user.LoginSession;
import com.shinhan.user.UserController;
import com.shinhan.user.UserView;

public class MainController {
	private static Scanner sc = new Scanner(System.in);
	private static UserController usercontroller = new UserController();
	private static FileController filecontroller = new FileController();
	private static FolderController foldercontroller = new FolderController();
	private static SharedLinkController sharedLinkcontroller = new SharedLinkController();
	private static ReportController reportcontroller = new ReportController();
	
	
	public static void main(String[] args) {
		boolean isStop = false;
		
		while(!isStop) {
			printMainMenu();
			String job = sc.next();
			
			if(LoginSession.getLoginUser() == null) {
				switch(job) {
					case "1" -> usercontroller.execute();
					case "0" -> {
						isStop = true;
						UserView.endDisplay("프로그램을 종료합니다.");
					}
					default -> {System.out.println("잘못된 선택입니다. 다시 선택해주세요.");}
				}
			} else {
				switch(job) {
					case "1" -> usercontroller.execute();
					case "2" -> foldercontroller.execute();
					case "3" -> filecontroller.execute();
					case "4" -> sharedLinkcontroller.execute();
					case "5" -> reportcontroller.execute();
					case "0" -> {
						isStop = true;
						UserView.endDisplay("프로그램을 종료합니다.");
					}
					default -> {System.out.println("잘못된 선택입니다. 다시 선택해주세요.");}
				}
			}
		} 
	}
	
	public static void printMainMenu() {
		System.out.println("---------- 클라우드 시스템 메인 메뉴 ----------");
		
		if(LoginSession.getLoginUser() == null) {
			System.out.println("1. 사용자 기능 (로그인 / 회원가입)");
		} else {
			System.out.println("1. 사용자 기능 (로그아웃)");
			System.out.println("2. 폴더 기능");
			System.out.println("3. 파일 기능");
			System.out.println("4. 공유 기능");
			System.out.println("5. 신고 기능");
		}
			System.out.println("0. 종료");
			System.out.print("작업을 선택하세요 >> ");
	}
	
	boolean isLogin() {
		if(LoginSession.getLoginUser() == null) {
			System.out.println("알림 : 먼저 로그인 해주세요.");
			return false;
		} 
		return true;	
	}
	
}
