package com.shinhan.folder;

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
public class FolderDAO {
	
	Connection conn = null;
	PreparedStatement st = null;
	ResultSet rs = null;
	
	// 1. 폴더 생성 
	public int createFolder (String folder_name, int user_id) {
		int result = 0;
		String sql = """
					insert into Folder (folder_id, folder_name, user_id, created_time)
					values(seq_folder_id.nextval, ?, ?, systimestamp)
				""";
		try {
			conn = DBUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setString(1, folder_name);
			st.setInt(2, user_id);
			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return result;
	}
	
	// 2. 폴더 삭제
	public int deleteFolder(int folder_id, int user_id) {
		int result = 0;
		String filesql = """
				delete from "File" where folder_id = ?
			""";
		String foldersql = """
					delete from Folder where folder_id = ?
					and user_id = ?
				""";
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(filesql);
			st.setInt(1, folder_id);
			st.executeUpdate();
			
			st = conn.prepareStatement(foldersql);
			st.setInt(1, folder_id);
			st.setInt(2, user_id);
			result = st.executeUpdate();
			
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		
		return result;
	}
	
	// 3. 폴더 목록 조회
	public List<FolderDTO> getFolderList(int user_id) {
		List<FolderDTO> list = new ArrayList<>();
		String sql = """
				select * from Folder where user_id = ? order by created_time desc
				""";
		try {
			conn = DBUtil.getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, user_id);
			rs = st.executeQuery();
			
			while(rs.next()) {
				FolderDTO dto = new FolderDTO();
				dto.setFolder_Id(rs.getInt("folder_id"));
				dto.setFolder_name(rs.getString("folder_name"));
				dto.setUser_id(rs.getInt("user_id"));
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




