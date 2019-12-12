package com.picaboo.nor.customer.mapper;

import java.util.*;
import org.apache.ibatis.annotations.Mapper;
import com.picaboo.nor.customer.vo.*;
import com.picaboo.nor.franchisee.vo.*;

@Mapper
public interface CustomerMapper {
	//좌석 출력
	public List<Seat> selectSeat(String franchiseeNo);
	//모든 프렌차이즈 정보를 가져옴
	public List<Franchisee> selectFranchiseeNo();
	//프렌차이즈 상세정보를 가져옴
	public Franchisee selectFranchisee(String franchiseeNo);
	//좌석 예약
	public void insertReservation(SeatReservation seatReservation);
	//예약한 좌석 타입 변경
	public void updateSeatType(SeatReservation seatReservation);
	//예약된 좌석 리스트
	public List<SeatReservation> selectSeatReservation(String franchiseeNo);
	//예약기간이 지난 예약 삭제
	public void deleteSeatReservation(SeatReservation seatReservation);

	public List<FranchiseePic> selectFranchiseeThumbnail();
	
	public List<FranchiseePic> selectFranchiseeThumbnailList(String franchiseeNo);
	
	public List<Food> selectFoodList(String franchiseeNo);
}