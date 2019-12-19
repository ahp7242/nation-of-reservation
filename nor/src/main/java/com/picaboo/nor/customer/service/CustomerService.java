package com.picaboo.nor.customer.service;

import java.util.List;
import com.picaboo.nor.customer.vo.*;
import com.picaboo.nor.franchisee.vo.*;

public interface CustomerService {
	// 가맹점 주소정보 조회
	public List<Franchisee> getFranchiseeAddress();
	// 회원 주소정보 조회
	public Customer getCustomerAddress(String customerNo);
	//데이터베이스의 저장된 좌석 가져오는 서비스
	public List<Seat> getSeat(String franchiseeNo);
	//프렌차이즈 번호 목록을 가져오는 서비스
	public List<Franchisee> getFranchiseeNo();
	//프렌차이즈 상세정보를 가져오는 서비스
	public Franchisee getFranchisee(String franchiseeNo);
	//좌석 예약을 하는 서비스
	public void addSeatReservation(SeatReservation seatReservation);
	//프렌차이즈의 대표이미지를 가져오는 서비스
	public List<FranchiseePic> getFranchiseeThumbnail();
	//선택된 프렌차이즈의 사진리스트를 가져오는 서비스
	public List<FranchiseePic> getFranchiseeThumbnailList(String franchiseeNo);
	//선택된 프렌차이즈에 등록된 상품 리스트를 가져오는 서비스
	public List<FoodInfo> getFoodList(String franchiseeNo);
	//프렌차이즈의 스펙 리스트를 가져오는 서비스
	public List<FranchiseeSpec> getFranchiseeSpecList();
	//상품을 예약하는 서비스
	public void addFoodReservation(FoodReservation foodReservation);
	//로그인한 고객의 좌석 예약기록의 카운트값 가져오는 서비스
	public List<MySeatReservationPer> getMySeatReservation(String customerNo);
	//로그인한 고객의 상품 예약기록의 카운트값 가져오는 서비스
	public List<MyFoodReservationPer> getMyFoodReservation(Franchisee franchisee);
	//로그인한 고객의 총 상품 주문 금액 가져오는 서비스
	public List<TotalPrice> getCustomerTotalPrice(String customerNo);
	//로그인한 고객의 모든 qna리스트를 가져오는 서비스
	public List<CustomerQnA> getCustomerQnAList(String customerNo);
	//로그인한 고객이 입력한 qna를 데이터베이스에 저장하는 서비스
	public int addCustomerQnA(CustomerQnA customerQnA);
	//1개의 qna를 가져오는 서비스
	public CustomerQnA getCustomerQnAOne(int qnaNo);
}
