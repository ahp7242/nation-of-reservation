package com.picaboo.nor.franchisee.vo;

import lombok.Data;
/*
 * pc 사양
 * specName: pc 부품 이름
 * specType: 부품 종류(CPU, VGA, RAM)
 */
@Data
public class Spec {
	private int specNo;
	private String specName;
	private String specType;
}
