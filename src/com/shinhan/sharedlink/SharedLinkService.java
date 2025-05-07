package com.shinhan.sharedlink;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import com.shinhan.user.LoginSession;

import lombok.extern.java.Log;

//서비스 :비지니스 로직을 수행한다. DB에 가는 업무는 DAO를 호출한다. 
@Log
public class SharedLinkService {
	SharedLinkDAO sharedLinkDAO = new SharedLinkDAO();

	// 1. 공유 링크 생성
		public String shareFile(int file_id, String visibility, Date expiration_date) {
			SharedLinkDTO dto = new SharedLinkDTO();
			int user_id = LoginSession.getLoginUser().getUser_id();
			dto.setFile_id(file_id);
			dto.setVisibility(visibility);
			dto.setExpiration_date(expiration_date);
			dto.setUser_id(user_id);
			String url = UUID.randomUUID().toString();
			dto.setLink_url("https://cloud.com/share/" + url);
			int result = sharedLinkDAO.shareFile(dto);
			return result > 0 ? "공유 링크 생성 성공\n공유 링크 >> " + dto.getLink_url() : "공유 링크 생성 실패";
		}

		// 2. 공유 링크 삭제
		public String deleteSharedLink(int link_id) {	
			int user_id = LoginSession.getLoginUser().getUser_id();
			int result = sharedLinkDAO.deleteSharedLink(link_id, user_id);
			return result > 0 ? "공유 링크 삭제 성공" : "공유 링크 삭제 실패";
		}
		
	// 3. 공유 링크 받기
		public List<SharedLinkDTO> getSharedLink() {
			int user_id = LoginSession.getLoginUser().getUser_id();
			return sharedLinkDAO.getSharedLink(user_id);
		}
}