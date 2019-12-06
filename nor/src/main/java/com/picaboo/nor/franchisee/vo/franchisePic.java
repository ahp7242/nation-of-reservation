package com.picaboo.nor.franchisee.vo;

import lombok.Data;

/*
 *  가맹점 사진 파일 VO
 *  franchisee_no: 가맹점 번호
 *  pic_no: 사진 번호
 *  filename: 저장된 파일 이름
 *  extension: 파일 확장자
 *  content_type: 컨텐츠 타입
 *  size: 파일 크기
 *  origin_name: 파일 원래 이름
 */


@Data
public class franchisePic {	
	private String franchiseeNo;
	private String picNo;
	private String fileName;
	private String extension;
	private String contentType;
	private String size;
	private String originName;

}
