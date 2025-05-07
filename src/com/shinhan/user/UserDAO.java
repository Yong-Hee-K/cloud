package com.shinhan.user;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.shinhan.util.DBUtil;

//비지니스 로직(업무로직)중에서 데이터베잇 관련업무는 DAO에서 작성한다. 
//CRUD작업 
//DAO(Data Access Object)
public class UserDAO {
	
	// 1. register(등록)
	public String RegisterUser(UserDTO user) {
		String result = "";
		String sql = "{call register_user(?, ?, ?, ?)}";
		
		
		try(Connection conn = DBUtil.getConnection();
		    CallableStatement st = conn.prepareCall(sql)) {
			
			st.setString(1, user.getEmail());
			st.setString(2, user.getPassword());
			st.setString(3, user.getRole());
			st.registerOutParameter(4, Types.VARCHAR);
			
			st.execute();
			
			result = st.getString(4);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return result;
	}
	
	
	
	// 2. 로그인
	public String LoginUser(String email, String password) {
		String result = "";
		String sql = "{call cloud_login_user(?, ?, ?)}";
		
		
		try(Connection conn = DBUtil.getConnection();
			CallableStatement st = conn.prepareCall(sql)) {
			
			st.setString(1, email);
			st.setString(2, password);
			st.registerOutParameter(3, Types.VARCHAR);
			
			st.execute();
			
			result = st.getString(3);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	// 3. 정보 조회
	public UserDTO selectByEmail(String email) {
	    UserDTO user = null;
	    String sql = "SELECT * FROM \"User\" WHERE email = ?";

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement st = conn.prepareStatement(sql)) {

	        st.setString(1, email);
	        ResultSet rs = st.executeQuery();

	        if (rs.next()) {
	            user = new UserDTO();
	            user.setUser_id(rs.getInt("user_id"));
	            user.setEmail(rs.getString("email"));
	            user.setPassword(rs.getString("password"));
	            user.setRole(rs.getString("role"));
	            user.setCreated_time(rs.getTimestamp("created_time"));
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return user;
	}

	
}