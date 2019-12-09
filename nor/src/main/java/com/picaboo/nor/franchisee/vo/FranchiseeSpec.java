package com.picaboo.nor.franchisee.vo;

import lombok.Data;

/*
 * 가맹점 정보 VO
 * franchiseeNo: 가맹점 번호
 * cpu, vga, ram: 가맹점 pc 사양
 */

@Data
public class FranchiseeSpec {
	private String franchiseeNo;
	private String cpu;
	private String vga;
	private String ram;
}
