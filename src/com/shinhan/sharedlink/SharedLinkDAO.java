package com.shinhan.sharedlink;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.shinhan.util.DBUtil;

//비지니스 로직(업무로직)중에서 데이터베잇 관련업무는 DAO에서 작성한다. 
//CRUD작업 
//DAO(Data Access Object)
public class SharedLinkDAO {
	Connection conn;
	PreparedStatement st;
	ResultSet rs;
	
	// 1. 파일 공유 
	public int shareFile(SharedLinkDTO dto) {
		int result = 0;
		
		String sql = """
				insert into sharedLink (
				link_id, file_id, link_url, visibility, expiration_date, created_time, user_id)
				values (seq_link_id.nextval, ?, ?, ?, ?, systimestamp, ?)
				""";
		
		try {
			conn = DBUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, dto.getFile_id());
			st.setInt(5, dto.getUser_id());
			st.setString(2, dto.getLink_url());
			st.setString(3, dto.getVisibility());
			st.setDate(4, dto.getExpiration_date());
			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		
		return result;
	}
	
	// 2. 공유 링크 삭제
	public int deleteSharedLink(int link_id, int user_id) {
		String sql = """
				delete from SharedLink where link_id = ?
				and user_id = ?
				""";
		int result = 0;
		
		try {
			conn = DBUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, link_id);
			st.setInt(2, user_id);
			result = st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		
		return result;
	}
	
	// 3. 공유 링크 받기
	public List<SharedLinkDTO> getSharedLink(int user_id) {
		List<SharedLinkDTO> list = new ArrayList<>();
		String sql = """
				select * from SharedLink where user_id = ?
				order by created_time desc
				""";
		
		try {
			conn = DBUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, user_id);
			rs = st.executeQuery();
			
			while(rs.next()) {
				SharedLinkDTO dto = new SharedLinkDTO();
				
				dto.setLink_id(rs.getInt("link_id"));
				dto.setFile_id(rs.getInt("file_id"));
				dto.setUser_id(rs.getInt("user_id"));
				dto.setLink_url(rs.getString("link_url"));
				dto.setVisibility(rs.getString("visibility"));
				dto.setExpiration_date(rs.getDate("expiration_date"));
				dto.setCreated_time(rs.getTimestamp("created_time"));
				list.add(dto);
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return list;
	}
}




