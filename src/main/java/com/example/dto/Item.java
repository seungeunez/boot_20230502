package com.example.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Item {

	
	private long no;
	private String name;
	private String content; // clob
	private long price;
	private long quantity;
	private Date regdate;
	public String seller; //판매자아이디
	
	private long imageNo; // 대표이미지 번호를 저장할 임시변수
	
}
