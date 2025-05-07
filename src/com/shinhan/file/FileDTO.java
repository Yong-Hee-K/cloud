package com.shinhan.file;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//DTO(Data Transfer Object)...data전송시 사용되는 객체의 틀(template)
//JavaBeans기술은 default생성자가 있어야한다.
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FileDTO {
	private int file_id;    
	private String file_name;  
	private int folder_id;       
	private String file_type;      
	private int file_size;
	private int user_id;
	private Timestamp created_time;            
}







