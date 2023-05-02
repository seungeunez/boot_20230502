package com.example.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"filedata"})
@AllArgsConstructor
@NoArgsConstructor
public class ItemImage {
    private long no;

	private String filename;
	private long filesize;
	private byte[] filedata; // blob
	private String filetype;
    
	private long itemno;
	private Date regdate;
}
