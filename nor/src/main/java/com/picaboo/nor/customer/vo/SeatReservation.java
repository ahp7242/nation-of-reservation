package com.picaboo.nor.customer.vo;

import lombok.Data;

@Data
public class SeatReservation {
	private String franchiseeNo;
	private String customerNo;
	private String customerName;
	private int seatNo;
	private String reservationDate;
	private String type;
}
