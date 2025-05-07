package com.shinhan.folder;

import java.util.Scanner;

import com.shinhan.user.LoginSession;

public class FolderController {
	static Scanner sc = new Scanner(System.in);
	static FolderService folderService = new FolderService();
	
	public void execute() {
	      boolean isStop = false;
	      while(!isStop) {
	    	  FolderView.folderDisplay();
	    	  String job = sc.nextLine(); 
	    	  switch(job) {
	    	  case "1"-> {f_create();}
	    	  case "2"-> {f_delete();}
	    	  case "3"-> {f_list();}
	    	  case "0"-> {isStop = true;}
	    	  default -> {FolderView.displayMessage("잘못된 입력입니다. 다시 입력하세요.");}
	    	  }
	      }
	      FolderView.endDisplay();
	}
	private void f_list() {
		FolderView.displayFolderList(folderService.getFolderList(LoginSession.getLoginUser().getUser_id()));
	}
	private void f_delete() {
		System.out.println("------- 폴더 삭제 -------");
		System.out.print("삭제할 폴더 ID 입력 >> ");
		int folder_id = Integer.parseInt(sc.nextLine());
		String result = folderService.deleteFolder(folder_id);
		FolderView.displayMessage(result);
	}
	
	private void f_create() {
		System.out.println("------- 폴더 생성 -------");
		System.out.print("폴더 이름 입력 >> ");
		String folder_name = sc.nextLine();
		String result = folderService.createFolder(folder_name);
		FolderView.displayMessage(result);
	}
}





