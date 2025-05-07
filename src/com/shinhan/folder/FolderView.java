package com.shinhan.folder;

import java.util.List;

//View(Web에서 JSP로 변경될 예정이다)
public class FolderView {

	public static void folderDisplay() {
		System.out.println("------- 폴더 관리 -------");
		System.out.println("1. 폴더 생성");
		System.out.println("2. 폴더 삭제");
		System.out.println("3. 폴더 목록 조회");
		System.out.println("0. 나가기");
		System.out.print("작업을 선택하세요 >> ");
	}
	
	
	public static void displayFolderList(List<FolderDTO> list) {
		System.out.println("------- 폴더 목록 -------");
		for(FolderDTO dto : list) {
			System.out.println("ID : " + dto.getFolder_Id() + ", 이름 : "
					+ dto.getFolder_name() + ", 생성 일자 : " + dto.getCreated_time());
		}
	}
	
	public static void displayMessage(String message) {
		System.out.println("알림:" + message);
		
	}
	
	public static void endDisplay() {
		System.out.println("이전 메뉴로 이동합니다.");
	}
	
	
}
