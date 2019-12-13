package com.picaboo.nor.franchisee.vo;

import lombok.Data;

@Data
public class FoodPic {
	private int foodNo;
	private String fileName;
	private String extension;
	private String contentType;
	private long size;
	private String originName;

}
