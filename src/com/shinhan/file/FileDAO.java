package com.shinhan.file;

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
public class FileDAO {
	Connection conn = null;
	PreparedStatement st = null;
	ResultSet rs = null;
	
	// 1. 파일 업로드 
	public int uploadFile(FileDTO dto) {
		int result = 0;
		String sql = """
					insert into \"File\" 
					(file_id, file_name, folder_id, file_size, file_type, user_id, created_time)
					values(seq_file_id.nextval, ?, ?, ?, ?, ?, systimestamp)
				""";
		try {
			conn = DBUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setString(1, dto.getFile_name());
			st.setInt(2, dto.getFolder_id());
			st.setInt(3, dto.getFile_size());
			st.setString(4, dto.getFile_type());
			st.setInt(5, dto.getUser_id());
			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return result;
	}
	
	// 2. 파일 삭제
	public int deleteFile(int file_id) {
		int result = 0;
		String sql = """
					delete from \"File\" where file_id = ?
				""";
		try {
			conn = DBUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, file_id);
			result = st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		
		return result;
	}
	
	// 3. 파일 목록 조회
	public List<FileDTO> listFiles(int folder_id, int user_id) {
		List<FileDTO> list = new ArrayList<>();
		String sql = """
				select * from \"File\" where folder_id = ?
				order by created_time desc
				""";
		try {
			conn = DBUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, folder_id);
			rs = st.executeQuery();
			
			while(rs.next()) {
				FileDTO dto = new FileDTO();
				dto.setFile_id(rs.getInt("file_id"));
				dto.setFile_name(rs.getString("file_name"));
				dto.setFolder_id(rs.getInt("folder_id"));
				dto.setUser_id(rs.getInt("user_id"));
				dto.setFile_size(rs.getInt("file_size"));
				dto.setFile_type(rs.getString("file_type"));
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
	
	// 4. 파일 다운로드 
	public FileDTO downloadFile(int file_id, int user_id) {
		FileDTO dto = null;
		String sql = """
					select * from "File" where file_id = ? and user_id = ?
				""";
		try {
			conn = DBUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, file_id);
			st.setInt(2, user_id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				dto = new FileDTO();
				dto.setFile_id(rs.getInt("file_id"));
				dto.setFolder_id(rs.getInt("folder_id"));
				dto.setFile_name(rs.getString("file_name"));
				dto.setFile_type(rs.getString("file_type"));
				dto.setFile_size(rs.getInt("file_size"));
				dto.setCreated_time(rs.getTimestamp("created_time"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return dto;
	}
		
}






