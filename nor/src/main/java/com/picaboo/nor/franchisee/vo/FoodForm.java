package com.picaboo.nor.franchisee.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FoodForm {
	private int foodNo;
	private String franchiseeNo;
	private String foodName;
	private String foodCategory;
	private int foodPrice;
	private MultipartFile foodPic;
}
