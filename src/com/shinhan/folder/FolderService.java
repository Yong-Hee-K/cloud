package com.shinhan.folder;

import java.util.List;

import com.shinhan.user.LoginSession;

import lombok.extern.java.Log;

@Log
public class FolderService {
	FolderDAO folderDAO = new FolderDAO();

	// 1. 폴더 생성
	public String createFolder(String folder_name) {
		int user_id = LoginSession.getLoginUser().getUser_id();
		int result = folderDAO.createFolder(folder_name, user_id);
		return result > 0 ? "폴더 생성 완료" : "폴더 생성 실패";
	}
	
	// 2. 폴더 삭제
	public String deleteFolder(int folder_id) {
		int user_id = LoginSession.getLoginUser().getUser_id();
		int result = folderDAO.deleteFolder(folder_id, user_id);
		return result > 0 ? "폴더 삭제 완료" : "폴더 삭제 실패";
	}

	// 3. 폴더 목록 조회
	public List<FolderDTO> getFolderList(int user_id) {
		return folderDAO.getFolderList(user_id);
	}
}



