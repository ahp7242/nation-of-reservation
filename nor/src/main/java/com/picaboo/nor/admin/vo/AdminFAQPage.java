package com.picaboo.nor.admin.vo;

import lombok.Data;

@Data
public class AdminFAQPage {
	private int beginRow;
	private int rowPerPage;
	private String searchWord;
}