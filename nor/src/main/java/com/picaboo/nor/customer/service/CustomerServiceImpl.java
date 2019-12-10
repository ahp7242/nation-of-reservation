package com.picaboo.nor.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.picaboo.nor.customer.mapper.CustomerMapper;
import com.picaboo.nor.customer.vo.*;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	@Autowired private CustomerMapper customerMapper;
	
	//데이터베이스의 저장된 좌석 가져오는 서비스
	@Override
	public List<Seat> getSeat(String franchiseeNo) {
		List<Seat> list = customerMapper.selectSeat(franchiseeNo);
		for(Seat l:list) {
			//System.out.println(l.getSeatUse());
			switch(l.getSeatUse()) {
				case "R":
					System.out.println(l.getSeatNo()+"예약");
					List<SeatReservation> seatList = customerMapper.selectSeatReservation(franchiseeNo);
					for (SeatReservation SL : seatList) {
						if(l.getSeatNo() == SL.getSeatNo()) {
							System.out.println(SL.getSeatNo());
							SeatReservation seatReservation = new SeatReservation();
							seatReservation.setFranchiseeNo(franchiseeNo);
							seatReservation.setSeatNo(SL.getSeatNo());
							System.out.println("예약 삭제");
							customerMapper.deleteSeatReservation(seatReservation);
							seatReservation.setType("N");
							System.out.println("좌석 타입 변경");
							customerMapper.updateSeatType(seatReservation);
						}
					}
			}
		}
		
		return list;
	}
	
	//프렌차이즈번호를 가져오는 서비스
	@Override
	public List<Franchisee> getFranchiseeNo() {
		return customerMapper.selectFranchiseeNo();
	}
	
	//프렌차이즈 상세정보를 가져오는 서비스
	@Override
	public Franchisee getFranchisee(String franchiseeNo) {
		
		return customerMapper.selectFranchisee(franchiseeNo);
	}
	
	//좌석 예약을 하는 서비스
	@Override
	public void addReservation(SeatReservation seatReservation) {
		System.out.println("service"+seatReservation);
		customerMapper.insertReservation(seatReservation);
		customerMapper.updateSeatType(seatReservation);
	}
}
