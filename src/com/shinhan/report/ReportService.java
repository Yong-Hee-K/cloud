package com.shinhan.report;

import java.util.List;

import com.shinhan.user.LoginSession;

import lombok.extern.java.Log;

//서비스 :비지니스 로직을 수행한다. DB에 가는 업무는 DAO를 호출한다. 
@Log
public class ReportService {
	ReportDAO reportDAO = new ReportDAO();

	// 1. 파일 신고
	public String reportFile(int file_id, String report_reason) {
		ReportDTO dto = new ReportDTO();
		int user_id = LoginSession.getLoginUser().getUser_id();
		dto.setFile_id(file_id);
		dto.setUser_id(user_id);
		dto.setReport_reason(report_reason);
		
		int result = reportDAO.reportFile(dto);
		return result > 0 ? "파일 신고 완료" : "파일 신고 실패";
	}

	// 2. 폴더 삭제
	public String deleteReport(int report_id) {
		int result = reportDAO.deleteReport(report_id);
		return result > 0 ? "신고 삭제 성공" : "신고 삭제 실패";
	}
		
	// 3. 파일 목록 조회
	public List<ReportDTO> listReports() {
		return reportDAO.listReports();
	}
}






