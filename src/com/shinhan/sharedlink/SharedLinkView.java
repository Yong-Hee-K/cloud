package com.shinhan.sharedlink;

import java.util.List;

//View(Web에서 JSP로 변경될 예정이다)
public class SharedLinkView {

	public static void SharedLinkDisplay() {
		System.out.println("------- 공유 관리 -------");
		System.out.println("1. 파일 공유");
		System.out.println("2. 공유 취소");
		System.out.println("3. 공유 목록 조회");
		System.out.println("0. 나가기");
		System.out.print("작업을 선택하세요 >> ");
	}
	
	public static void displayMessage(String message) {
		System.out.println("알림 : " + message);
	}
	
	public static void display(String message) {
		System.out.println("이전 메뉴로 이동합니다.");
	}
	
	public static void displaySharedLinkList(List<SharedLinkDTO> list) {
		if(list.isEmpty()) {
			System.out.println("공유된 링크가 없습니다.");
			return;
		}
		
		System.out.println("------- 공유 목록 -------");
		for(SharedLinkDTO dto : list) {
			System.out.printf("링크 ID : %d, 파일 ID : %d, 공개 여부 : %s, 공유 시각 : %s%n",
					dto.getLink_id(), dto.getFile_id(), dto.getVisibility(), dto.getCreated_time());
		}
	}
	
	
}
