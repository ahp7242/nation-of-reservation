package com.picaboo.nor.membership.vo;

import lombok.Data;

@Data
public class SignForm {
	private String customerNo;
	private String customerId;
	private String customerPw;
	private String customerName;
	private String customerBirth;
	private String customerGender;
	private String customerEmail;
	private String customerPhone;
	private String customerType;
	private int addressNo; 		
	private String roadAddress; //도로명
	private String jibunAddress; //지번
	private String detailAddress; //상세주소
	private String postcode; //우편번호
}
