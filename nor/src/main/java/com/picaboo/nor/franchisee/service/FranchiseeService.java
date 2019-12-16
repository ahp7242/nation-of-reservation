package com.picaboo.nor.franchisee.service;

import java.util.List;
import java.util.Map;

import com.picaboo.nor.franchisee.vo.FoodForm;
import com.picaboo.nor.franchisee.vo.FoodReservationList;
import com.picaboo.nor.franchisee.vo.Franchisee;
import com.picaboo.nor.franchisee.vo.FranchiseeInfoForm;
import com.picaboo.nor.franchisee.vo.FranchiseeOwner;
import com.picaboo.nor.franchisee.vo.FranchiseeQnA;
import com.picaboo.nor.franchisee.vo.Seat;

public interface FranchiseeService {
	
	// 가맹점 상품 삭제
	public int removeFranchiseeFood(int foodNo);
	// 주문완료 상품 삭제
	public int delFoodReservation(int reservationNo);
	// 음식 리스트 확인 서비스
	public List<FoodReservationList> getFoodReservationList(String franchiseeNo);
	// 가맹점 상품 리스트 조회
	public Map<String,Object> getFranchiseeFood(String franchiseeNo);
	// 가맹점 상품 등록
	public int addFranchiseeFood(FoodForm foodForm);
	// 회원문의 정보 확인 서비스
	public List<FranchiseeQnA> getFranchiseeQnaList(String ownerNo);	
	// 회원의 상세정보를 수정하는 서비스
	public int modifyFranchiseeOwner(FranchiseeOwner franchiseeOwner);	
	// 회원의 상세정보를 가져오는 서비스
	public FranchiseeOwner detailFranchiseeOwner(String ownerNo);
	// 가맹점 썸네일 사진 조회
	public Map<String, Object> getFranchiseeThumbnail(List<Franchisee> franchiseeList);
	// 가맹점 정보 수정
	public int modifyFranchiseeInfo(FranchiseeInfoForm franchiseeInfoForm);
	// QnA 등록
	public int addFranchiseeQnA(FranchiseeQnA franchiseeQnA);
	// pc 사양 리스트 조회
	public Map<String, Object> getSpec();
	// FAQ 리스트 조회
	public Map<String, Object> getFranchiseeFAQ(int currentPage, int rowPerPage, String searchWord);
	// 가맹점 정보 조회
	public Map<String, Object> getFranchiseeInfo(String franchiseeNo);
	// 가맹점 정보 입력
	public int addFranchiseeInfo(FranchiseeInfoForm FranchiseeInfoForm);
	// 가맹점 좌석 삭제
	public int removeSeat(String franchiseeNo);
	// 가맹점 좌석정보 조회 
	public List<Seat> getSeat(String franchiseeNo);
	// 가맹점 상세정보 조회
	public Franchisee getFranchiseeOne(String franchiseeNo); 
	// 가맹점 리스트 조회
	public List<Franchisee> getFranchiseeList(String ownerNo);
	// 좌석 입력
	public int addFranchiseeSeat(Map<String, String> seatMap);
	// 가맹점 신청
	public int addFranchisee(Franchisee franchisee);
	
}
