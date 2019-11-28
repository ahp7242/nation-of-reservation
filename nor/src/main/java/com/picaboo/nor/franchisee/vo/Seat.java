package com.picaboo.nor.franchisee.vo;

import lombok.Data;

@Data
public class Seat {
	private String franchiseeNo;
	private int seatNo;
	private int seatRow;
	private int seatCols;
	private String seatUse;
}
