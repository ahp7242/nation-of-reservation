package com.picaboo.nor.franchisee.vo;

import lombok.Data;

/*
 * Food + FoodReservation 조인
 * reservationNo: 예약 번호
 * foodNo: 상품 번호
 * reservationCount: 예약 수량 
 * customerNo: 고객 번호
 * customerName: 고객 이름
 * reservationDate: 예약 시간
 */


@Data
public class FoodReservationList {
	private int reservationNo;
	private int foodNo;
	private int reservationCount;
	private String customerNo;
	private String customerName;
	private String reservationDate;

}
