package com.picaboo.nor.franchisee.vo;

import lombok.Data;
/*
 * 가맹점주 정보
 * 번호, ID, PW, 이름, 생년월일, 성별, 이메일, 전화번호, 고객타입: 'O'
 */
@Data
public class FranchiseeOwner {
	private String customerNo;
	private String customerId;
	private String customerPw;
	private String customerName;
	private String customerBirth;
	private String customerGender;
	private String customerEmail;
	private String customerPhone;
	private String customerType;

}
