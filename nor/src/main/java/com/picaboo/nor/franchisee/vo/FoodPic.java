package com.picaboo.nor.franchisee.vo;

import lombok.Data;

/*
 *  가맹점 상품 사진 파일 VO
 *  foodNo: 가맹점 번호
 *  fileName: 저장된 파일 이름
 *  extension: 파일 확장자
 *  contentType: 컨텐츠 타입
 *  size: 파일 크기
 *  originName: 파일 원래 이름
 */
@Data
public class FoodPic {
	private int foodNo;
	private String fileName;
	private String extension;
	private String contentType;
	private long size;
	private String originName;

}
