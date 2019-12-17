package com.picaboo.nor.franchisee.vo;

import lombok.Data;

@Data
public class SeatReservationList {
	private int seatNo;
	private String franchiseeNo;
	private String customerNo;
	private String customerName;
	private String reservationDate;
}
