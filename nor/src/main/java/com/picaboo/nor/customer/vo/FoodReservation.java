package com.picaboo.nor.customer.vo;

import lombok.Data;

@Data
public class FoodReservation {
	private int reservationNo;
	private int foodNo;
	private int reservationCount;
	private String reservationDate;
	private String customerNo;
	private String customerName;
}
