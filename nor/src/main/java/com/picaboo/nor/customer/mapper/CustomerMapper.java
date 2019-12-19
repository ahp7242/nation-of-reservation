package com.picaboo.nor.customer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.picaboo.nor.customer.vo.Customer;
import com.picaboo.nor.customer.vo.CustomerQnA;
import com.picaboo.nor.customer.vo.FoodInfo;
import com.picaboo.nor.customer.vo.FoodReservation;
import com.picaboo.nor.customer.vo.MyFoodReservationPer;
import com.picaboo.nor.customer.vo.MySeatReservationPer;
import com.picaboo.nor.customer.vo.SeatReservation;
import com.picaboo.nor.customer.vo.TotalPrice;
import com.picaboo.nor.franchisee.vo.Franchisee;
import com.picaboo.nor.franchisee.vo.FranchiseePic;
import com.picaboo.nor.franchisee.vo.FranchiseeSpec;
import com.picaboo.nor.franchisee.vo.Seat;

@Mapper
public interface CustomerMapper {
	// 가맹점 주소 조회
	public List<Franchisee>selectFranchiseeAddressList();
	// 회원 주소 정보 조회
	public Customer selectCustomerAddress(String customerNo);
	//좌석 출력
	public List<Seat> selectSeat(String franchiseeNo);
	//모든 프렌차이즈 정보를 가져옴
	public List<Franchisee> selectFranchiseeNo();
	//프렌차이즈 상세정보를 가져옴
	public Franchisee selectFranchisee(String franchiseeNo);
	//좌석 예약
	public void insertSeatReservation(SeatReservation seatReservation);
	//예약한 좌석 타입 변경
	public void updateSeatType(SeatReservation seatReservation);
	//예약된 좌석중 시간이 10분 지난 리스트
	public List<SeatReservation> selectSeatReservation(String franchiseeNo);
	//예약기간이 지난 예약 을 cancel테이블로 이동
	public void insertCancelSeatReaservation(SeatReservation seatReservation);
	//예약기간이 지난 예약 삭제
	public void deleteSeatReservation(SeatReservation seatReservation);
	//프렌차이즈의 대표 이미지 가져옴
	public List<FranchiseePic> selectFranchiseeThumbnail();
	//선택된 프렌차이즈의 이미지를 가져옴
	public List<FranchiseePic> selectFranchiseeThumbnailList(String franchiseeNo);
	//선택된 프렌차이즈에 등록된 상품목록을 가져옴
	public List<FoodInfo> selectFoodList(String franchiseeNo);
	//프렌차이즈의 스펙을 가져옴
	public List<FranchiseeSpec> selectSpecList();
	//예약한 상품을 데이터베이스에 저장
	public void insertFoodReservation(FoodReservation foodReservation);
	//예약된 상품의 리스트를 가져옴
	public List<FoodReservation> selectFoodReservation(String franchiseeNo);
	//예약된 상품중 기간이 10분지난 목록을 cancel테이블로 이동
	public void insertCancelFoodReservation(FoodReservation foodreservation);
	//예약된 상품중 기간이 10분지난 목록을 예약 테이블에서 삭제
	public void deleteFoodReservation(int reservationNo);
	//로그인한 고객의 좌석 예약 기록의 카운트값을 가져옴
	public List<MySeatReservationPer> selectMySeatReservationList(String customerNo);
	//로그인한 고객의  상품 예약 기록의 카운트값을 가져옴
	public List<MyFoodReservationPer> selectMyFoodReservationList(Franchisee franchisee);
	//로그인한 고객의 총 상품 주문 금액 가져옴
	public List<TotalPrice> selectCustomerTotalPrice(String customerNo);
	//로그인한 고객의 답변이 달리지 않은 qna리스트를 가져옴
	public List<CustomerQnA> selectCustomerQnAList(String customerNo);
	//로그인한 고객작성한 qna 등록 
	public int insertCustomerQnA(CustomerQnA customerQnA);
	//1개의 qna내용을 가져옴
	public CustomerQnA selectCustomerQnAOne(int qnaNo);
}