package com.picaboo.nor.customer.vo;

import lombok.Data;

@Data
public class CustomerQnA {
	private int qnaNo;
	private String customerNo;
	private String qnaType;
	private String qnaTitle;
	private String qnaQuestion;
	private String qnaAnswer;
	private String qnaDate;
	private String customerMail;
}
