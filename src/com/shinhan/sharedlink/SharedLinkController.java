package com.shinhan.sharedlink;

import java.sql.Date;
import java.util.Scanner;

public class SharedLinkController {
	static Scanner sc = new Scanner(System.in);
	static SharedLinkService sharedLinkService = new SharedLinkService();

	public void execute() {
	      boolean isStop = false;
	      while(!isStop) {
	    	  SharedLinkView.SharedLinkDisplay();
	    	  String job = sc.nextLine(); 
	    	  switch(job) {
	    	  case "1"-> {shareFile();}
	    	  case "2"-> {deleteLink();}
	    	  case "3"-> {listLinks();}
	    	  case "0"-> {isStop = true;}
	    	  default -> {SharedLinkView.displayMessage("잘못된 입력입니다. 다시 입력하세요.");}
	    	  }
	      }
	      SharedLinkView.display("프로그램 종료");
	}
	private void listLinks() {
		SharedLinkView.displaySharedLinkList(sharedLinkService.getSharedLink());
	}
	private void deleteLink() {
		System.out.println("------- 공유 링크 삭제 -------");
		System.out.print("삭제할 링크 ID 입력 >> ");
		int link_id = Integer.parseInt(sc.nextLine());
		String result = sharedLinkService.deleteSharedLink(link_id);
		SharedLinkView.displayMessage(result);
	}
	private void shareFile() {
		System.out.println("------- 공유 링크 생성 -------");
		System.out.print("공유할 파일 ID 입력 >> ");
		int file_id = Integer.parseInt(sc.nextLine());
		System.out.print("공개 여부 (private / public) >> ");
		String visibility = sc.nextLine();
		System.out.print("기한 설정(ex :0000-00-00) >> ");
		Date exp = Date.valueOf(sc.nextLine());
		String result = sharedLinkService.shareFile(file_id, visibility, exp);
		SharedLinkView.displayMessage(result);
	}
}








