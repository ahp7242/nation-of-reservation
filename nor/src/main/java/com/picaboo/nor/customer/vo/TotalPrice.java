package com.picaboo.nor.customer.vo;

import lombok.Data;

@Data
public class TotalPrice {
	private int foodNo;
	private int sum;
	private String customerNo;
	private String customerName;
	private int foodPrice;
	private int totalPrice;
}
