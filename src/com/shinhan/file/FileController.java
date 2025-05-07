package com.shinhan.file;

import java.util.Scanner;

public class FileController {
	static Scanner sc = new Scanner(System.in);
	static FileService fileService = new FileService();
	
	public void execute() {
	      boolean isStop = false;
	      while(!isStop) {
	    	  FileView.fileDisplay();
	    	  String job = sc.nextLine(); 
	    	  switch(job) {
	    	  case "1"-> {uploadFile();}
	    	  case "2"-> {deleteFile();}
	    	  case "3"-> {listFiles();}
	    	  case "4"-> {downloadFile();}
	    	  case "0"-> {isStop = true;}
	    	  default -> {FileView.displayMessage("잘못된 입력입니다. 다시 입력하세요.");}
	    	  }
	      }
	      FileView.endDisplay();
	}
	private void listFiles() {
		System.out.println("------- 폴더 내 파일 조회 -------");
		System.out.print("조회할 폴더 ID 입력 >> ");
		int folder_id = Integer.parseInt(sc.nextLine());
		
		FileView.displayFileList(fileService.listFiles(folder_id));
	}
	private void deleteFile() {
		System.out.println("------- 파일 삭제 -------");
		System.out.print("삭제할 파일 번호 입력 >> ");
		int file_id = Integer.parseInt(sc.nextLine());
		String result = fileService.deleteFile(file_id);
		FileView.displayMessage(result);
	}
	private void uploadFile() {
		System.out.println("------- 파일 업로드 -------");
		System.out.print("파일 이름 입력 >> ");
		String file_name = sc.nextLine();
		System.out.print("폴더 ID 입력 >> ");
		int folder_id = Integer.parseInt(sc.nextLine());
		System.out.print("파일 크기 입력 >> ");
		int file_size = Integer.parseInt(sc.nextLine());
		System.out.print("파일 확장자 입력 >> ");
		String file_type = sc.nextLine();
		String result = fileService.uploadFile(file_name, folder_id, file_size, file_type);
		FileView.displayMessage(result);
	}
	
	private void downloadFile() {
		System.out.println("------- 파일 다운로드 -------");
		System.out.print("다운로드 할 파일 ID 입력 >> ");
		int file_id = Integer.parseInt(sc.nextLine());
		String result = fileService.downloadFile(file_id);
		FileView.displayMessage(result);
	}
}










