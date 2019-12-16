package com.picaboo.nor.franchisee.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
/*
 * 가맹점 상품입력 폼 VO
 * foodNo: 상품 번호
 * franchiseeNo: 가맹점 번호
 * foodName: 상품 이름
 * foodCategory: 상품 카테고리
 * foodPrice: 상품 가격
 * foodPic: 상품 사진
 */
@Data
public class FoodForm {
	private int foodNo;
	private String franchiseeNo;
	private String foodName;
	private String foodCategory;
	private int foodPrice;
	private MultipartFile foodPic;
}
