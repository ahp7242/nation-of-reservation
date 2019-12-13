package com.picaboo.nor.franchisee.vo;

import lombok.Data;

@Data
public class FranchiseeQnA {
	private int qnaNo;
	private String customerNo;
	private String qnaType;
	private String qnaTitle;
	private String qnaQuestion;
	private String qnaAnswer;
	private String qnaDate;
	private String customerMail;
}