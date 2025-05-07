package com.shinhan.report;

import java.util.List;

//View(Web에서 JSP로 변경될 예정이다)
public class ReportView {

	public static void reportDisplay() {
		System.out.println("------- 신고 관리 -------");
		System.out.println("1. 파일 신고");
		System.out.println("2. 신고 삭제(관리자)");
		System.out.println("3. 신고 목록 조회(관리자)");
		System.out.println("0. 나가기");
		System.out.print("작업을 선택하세요 >> ");
	}
	
	public static void displayMessage(String message) {
		System.out.println("알림:" + message);
	}
	
	public static void display(String message) {
		System.out.println("이전 메뉴로 이동합니다.");
	}
	
	public static void displayReportList(List<ReportDTO> list) {
		if(list.isEmpty()) {
			System.out.println("신고 내역이 없습니다.");
		} else {
		System.out.println("------- 신고 목록 -------");
		for(ReportDTO dto : list) {
			System.out.printf("신고 ID : %d, 파일 ID : %d, 신고자 ID : %d, 신고 사유 : %s, 신고 시각 : %s%n",
			dto.getReport_id(), dto.getFile_id(), dto.getUser_id(), dto.getReport_reason(), dto.getCreated_Time());
			System.out.println();
		}
	}
}
	
	
}
