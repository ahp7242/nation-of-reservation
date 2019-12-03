package com.picaboo.nor.franchisee.vo;

import lombok.Data;

/*
 * 좌석 VO
 * 좌석 등록 시 번호, 위치 
 */

@Data
public class Seat {
	private int seatNo;
	private int seatRow;
	private int seatCols;
	private String seatUse;
	private Franchisee franchisee;
}
