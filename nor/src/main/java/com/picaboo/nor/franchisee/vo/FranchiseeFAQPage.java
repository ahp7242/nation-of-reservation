package com.picaboo.nor.franchisee.vo;

import lombok.Data;

@Data
public class FranchiseeFAQPage {
	private int beginRow;
	private int rowPerPage;
	private String searchWord;
}