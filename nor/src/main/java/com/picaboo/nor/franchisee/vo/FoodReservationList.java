package com.picaboo.nor.franchisee.vo;

import lombok.Data;

@Data
public class FoodReservationList {
	private int reservationNo;
	private int foodNo;
	private int reservationCount;
	private String customerNo;
	private String customerName;
	private String reservationDate;

}
