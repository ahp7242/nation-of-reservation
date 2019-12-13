package com.picaboo.nor.membership.vo;

import lombok.Data;

@Data
public class Address {
	private int addressNo; 		
	private String roadAddress; //도로명
	private String jibunAddress; //지번
	private String detailAddress; //상세주소
	private String postcode; //우편번호
}
