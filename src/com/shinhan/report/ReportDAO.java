package com.shinhan.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shinhan.util.DBUtil;

//비지니스 로직(업무로직)중에서 데이터베잇 관련업무는 DAO에서 작성한다. 
//CRUD작업 
//DAO(Data Access Object)
public class ReportDAO {
	Connection conn;
	PreparedStatement st;
	ResultSet rs;

	
	// 1. 파일 신고
	public int reportFile(ReportDTO dto) {
		int result = 0;
		String sql = """
				insert into Report (report_id, user_id, file_id, report_reason, created_time)
				values(seq_report_id.nextval, ?, ?, ?, systimestamp)  
				""";
		
		try {
			conn = DBUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, dto.getUser_id());
			st.setInt(2, dto.getFile_id());
			st.setString(3, dto.getReport_reason());
			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		
		return result;
	}
	
	// 2. 신고 삭제
	public int deleteReport(int report_id) {
		int result = 0;
		String sql = """
				delete from Report where report_id = ?
				""";
		
		try {
			conn = DBUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, report_id);
			result = st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		
		return result;
	}
	
	// 3. 신고 목록 조회
	public List<ReportDTO> listReports() {
		List<ReportDTO> list = new ArrayList<>();
		String sql = """
				select * from Report order by created_time desc
				""";
		try {
			conn = DBUtil.getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while(rs.next()) {
				ReportDTO dto = new ReportDTO();
				
				dto.setReport_id(rs.getInt("report_id"));
				dto.setUser_id(rs.getInt("user_id"));
				dto.setFile_id(rs.getInt("file_id"));
				dto.setReport_reason(rs.getString("report_reason"));
				dto.setCreated_Time(rs.getTimestamp("created_time"));
				
				list.add(dto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null);
		}
		return list;
	}
	
}






