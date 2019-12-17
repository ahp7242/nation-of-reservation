package com.picaboo.nor.franchisee.vo;

import lombok.Data;

@Data
public class FranchiseeForm {
	private String franchiseeNo;
	private String franchiseeCrn;
	private String franchiseeName;
	private String franchiseePhone;
	private String ownerNo; 		
	private String roadAddress; //도로명
	private String jibunAddress; //지번
	private String detailAddress; //상세주소
	private String postcode; //우편번호
}
