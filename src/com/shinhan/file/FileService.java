package com.shinhan.file;

import java.util.List;

import com.shinhan.user.LoginSession;

import lombok.extern.java.Log;

//서비스 :비지니스 로직을 수행한다. DB에 가는 업무는 DAO를 호출한다. 
@Log
public class FileService {
	FileDAO fileDAO = new FileDAO();

	// 1. 폴더 생성 
	public String uploadFile(String file_name, int folder_id, int file_size, String file_type) {
		FileDTO dto = new FileDTO();
		
		dto.setFile_name(file_name);
		dto.setFolder_id(folder_id);
		dto.setFile_size(file_size);
		dto.setFile_type(file_type);
		int user_id = LoginSession.getLoginUser().getUser_id();
		dto.setUser_id(user_id);
		
		int result = fileDAO.uploadFile(dto);
		return result > 0 ? "파일 업로드 성공" : "파일 업로드 실패";
	}
	
	// 2. 폴더 삭제
	public String deleteFile(int file_id) {
		int result = fileDAO.deleteFile(file_id);
		return result > 0 ? "파일 삭제 성공" : "파일 삭제 실패";
	}
	
	// 3. 파일 목록 조회
	public List<FileDTO> listFiles(int folder_id) {
		int user_id = LoginSession.getLoginUser().getUser_id();
		return fileDAO.listFiles(folder_id, user_id);
	}
	
	// 4. 파일 다운로드
	public String downloadFile(int file_id) {
		int user_id = LoginSession.getLoginUser().getUser_id();
		FileDTO file = fileDAO.downloadFile(file_id, user_id);
		
		return file != null ? 
				"파일 다운로드 완료 : " + file.getFile_name() + "." + file.getFile_type() +
				" (" + file.getFile_size() + "KB)" : "해당 번호의 파일이 존재하지 않습니다.";
		
	}
}






