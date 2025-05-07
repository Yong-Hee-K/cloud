package com.shinhan.file;

import java.util.List;

//View(Web에서 JSP로 변경될 예정이다)
public class FileView {

	public static void fileDisplay() {
		System.out.println("------- 파일 관리 -------");
		System.out.println("1. 파일 업로드");
		System.out.println("2. 파일 삭제");
		System.out.println("3. 파일 목록 조회");
		System.out.println("4. 파일 다운로드");
		System.out.println("0. 나가기");
		System.out.print("작업을 선택하세요 >> ");
	}
	
	
	public static void displayFileList(List<FileDTO> list) {
		System.out.println("------- 파일 목록 -------");
		if(list.isEmpty()) {
			System.out.println("폴더 내에 파일이 존재하지 않습니다.");
			return;
		}
		
		for(FileDTO dto : list) {
			System.out.printf("파일 ID : %d, 파일명 : %s, 확장자 : %s, 크기 : %dKB, 등록자 : %d, 등록일 : %s%n",
			dto.getFile_id(), dto.getFile_name(), dto.getFile_type(), dto.getFile_size(), dto.getUser_id(), dto.getCreated_time());
		}
	}
	
	public static void displayMessage(String message) {
		System.out.println("알림:" + message);
		
	}
	
	public static void endDisplay() {
		System.out.println("이전 메뉴로 이동합니다.");
	}
	
	
}
