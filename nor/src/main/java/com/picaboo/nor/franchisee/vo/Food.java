package com.picaboo.nor.franchisee.vo;

import lombok.Data;

@Data
public class Food {
	private int foodNo;
	private Franchisee franchisee;
	private String foodName;
	private String foodCategory;
	private int foodPrice;
}
