package com.shinhan.sharedlink;

import java.sql.Date;
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
public class SharedLinkDTO {
	private int link_id;    
	private int file_id;  
	private int user_id;
	private String link_url;
	private String visibility;
	private Date expiration_date;
	private Timestamp created_time;      
}







