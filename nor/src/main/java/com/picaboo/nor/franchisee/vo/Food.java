package com.picaboo.nor.franchisee.vo;

import lombok.Data;

/*
 * 가맹점 상품 VO
 * foodNo: 상품 번호
 * franchisee: 가맹점 번호
 * foodName: 상품 이름
 * foodCategory: 상품 카테고리
 * foodPrice: 상품 가격
 */

@Data
public class Food {
	private int foodNo;
	private Franchisee franchisee;
	private String foodName;
	private String foodCategory;
	private int foodPrice;
}
