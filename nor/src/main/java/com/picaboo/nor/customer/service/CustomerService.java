package com.picaboo.nor.customer.service;

import java.util.List;

import com.picaboo.nor.customer.vo.*;

public interface CustomerService {
	//데이터베이스의 저장된 좌석 가져오는 서비스
	public List<Seat> getSeat(String franchiseeNo);
	//프렌차이즈 번호 목록을 가져오는 서비스
	public List<Franchisee> getFranchiseeNo();
	//프렌차이즈 상세정보를 가져오는 서비스
	public Franchisee getFranchisee(String franchiseeNo);
	//좌석 예약을 하는 서비스
	public void addReservation(SeatReservation seatReservation);
}
