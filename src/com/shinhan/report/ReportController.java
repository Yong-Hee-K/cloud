package com.shinhan.report;

import java.util.Scanner;

import com.shinhan.user.LoginSession;

public class ReportController {
	static Scanner sc = new Scanner(System.in);
	static ReportService reportService = new ReportService();

	public void execute() {
	      boolean isStop = false;
	      while(!isStop) {
	    	  ReportView.reportDisplay();
	    	  String job = sc.nextLine(); 
	    	  switch(job) {
	    	  case "1"-> {reportFile();}
	    	  case "2"-> {deleteReport();}
	    	  case "3"-> {listReports();}
	    	  case "0"-> {isStop = true;}
	    	  default -> {ReportView.displayMessage("잘못된 입력입니다. 다시 입력하세요.");}
	    	  case "exit"->{}
	    	  }
	      }
	      ReportView.display("프로그램 종료");
	}

	private void listReports() {
		if(!LoginSession.getLoginUser().getRole().equals("admin")) {
			ReportView.displayMessage("관리자만 조회할 수 있습니다.");
			return;
		}
		ReportView.displayReportList(reportService.listReports());
	}

	private void deleteReport() {
		if(!LoginSession.getLoginUser().getRole().equals("admin")) {
			ReportView.displayMessage("관리자만 삭제할 수 있습니다.");
			return;
		}
		System.out.println("------- 신고 삭제 -------");
		System.out.print("삭제할 신고 ID 입력 >> ");
		int report_id = Integer.parseInt(sc.nextLine());
		
		String result = reportService.deleteReport(report_id);
		ReportView.displayMessage(result);
	}

	private void reportFile() {
		System.out.println("------- 파일 신고 -------");
		System.out.print("신고할 파일 ID 입력 >> ");
		int file_id = Integer.parseInt(sc.nextLine());
		System.out.print("신고 사유 입력 >> ");
		String report_reason = sc.nextLine();
		
		String result = reportService.reportFile(file_id, report_reason);
		ReportView.displayMessage(result);
	}
	
}










