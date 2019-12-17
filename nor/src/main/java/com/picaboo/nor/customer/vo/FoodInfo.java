package com.picaboo.nor.customer.vo;

import lombok.Data;

@Data
public class FoodInfo {
	private int foodNo;
	private String franchiseeNo;
	private String foodName;
	private String foodCategory;
	private int foodPrice;
	private String fileName;
	private String extension;
	private String contentType;
	private long size;
	private String originName;
}
