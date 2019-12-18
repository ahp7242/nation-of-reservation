package com.picaboo.nor.franchisee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.picaboo.nor.franchisee.vo.Food;
import com.picaboo.nor.franchisee.vo.FoodPic;
import com.picaboo.nor.franchisee.vo.FoodReservationList;
import com.picaboo.nor.franchisee.vo.FoodStatement;
import com.picaboo.nor.franchisee.vo.Franchisee;
import com.picaboo.nor.franchisee.vo.FranchiseeFAQ;
import com.picaboo.nor.franchisee.vo.FranchiseeFAQPage;
import com.picaboo.nor.franchisee.vo.FranchiseeOwner;
import com.picaboo.nor.franchisee.vo.FranchiseePic;
import com.picaboo.nor.franchisee.vo.FranchiseeQnA;
import com.picaboo.nor.franchisee.vo.FranchiseeSpec;
import com.picaboo.nor.franchisee.vo.Seat;
import com.picaboo.nor.franchisee.vo.SeatReservationList;
import com.picaboo.nor.franchisee.vo.Spec;
import com.picaboo.nor.franchisee.vo.TodayStatement;
import com.picaboo.nor.franchisee.vo.TotalStatement;
import com.picaboo.nor.membership.vo.Address;

@Mapper
public interface FranchiseeMapper {
    // cancel_seat_reservation 테이블에 INSERT 수행 
	public int cancelInsertSeatReservation(SeatReservationList seatReservationList);
    // seat 테이블에 use를 UPDATE 수행 N로 업데이트
	public int cancelUpdateSeatReservation(SeatReservationList seatReservationList);	
	// 좌석 예약 취소
	public int cancelSeatReservation(SeatReservationList seatReservationList);	
    // del_seat_reservation 테이블에 INSERT 수행 
	public int insertSeatReservation(SeatReservationList seatReservationList);
    // seat 테이블에 use를 UPDATE 수행 Y로 업데이트
	public int updateSeatReservation(SeatReservationList seatReservationList);
	// QnA 상세보기
	public List<FranchiseeQnA> selectQnaDetail(int qnaNo);	
	// 주소추가
	public int insertAddress(Address address);
	// 좌석 예약 취소
	public int delSeatReservation(SeatReservationList seatReservationList);
	// 좌석 예약 확인 서비스
	public List<SeatReservationList> selectSeatReservationList(String franchiseeNo);	
	// 상품에따른 가맹점별 매출 현황
	public List<TotalStatement> selectTotalStatementList(String ownerNo);
	// 오늘 매출 가맹점별 매출 현황
	public List<TodayStatement> selectTodayStatementList (String ownerNo);
	// 음식 주문 통계
	public List<FoodStatement> selectFoodfoodStatementList (FoodStatement foodStatement);
	// 가맹점 상품 수정
	public int updateFranchiseeFood(Food food);
	// 가맹점 상품 조회 
	public Food selectFood(int foodNo);
	// 가맹점 상품 사진 파일 조회
	public FoodPic selectFoodPic(int foodNo);
	// 가맹점 상품 사진 삭제
	public int deletefranchiseeFoodPic(int foodNo);
	// 가맹점 상품 삭제
	public int deletefranchiseeFood(int foodNo);
	// food 통계 계수 추가
	public int addFoodReservationCancel(int reservationNo);
	// 주문취소
	public int delFoodReservationCancel(int reservationNo);
	// food 통계 계수 추가
	public int addFoodReservation(int reservationNo);
	// 주문완료 음식 삭제
	public int delFoodReservation(int reservationNo);
	// 음식 주문 확인 서비스
	public List<FoodReservationList> selectFoodReservationList(String franchiseeNo);
	// 가맹점 상품 사진 조회
	public List<FoodPic> selectFoodPicList(String franchiseeNo);
	// 가맹점 상품 리스트 조회
	public List<Food> selectFoodList(String franchiseeNo, String foodCategory);
	// 가맹점 상품 사진 등록
	public int insertFranchiseeFoodPic(FoodPic foodPic);
	// 가맹점 상품 등록
	public int insertFranchiseeFood(Food food);
	// 고객 문의사항 정보 매퍼
	public List<FranchiseeQnA> selectFranchiseeQnaList(String ownerNo);
	// 고객의 상세정보를 수정하는 매퍼
	public int updateFranchiseeOwner(FranchiseeOwner franchiseeOwner);
	// 고객의 상세정보를 불러오는 매퍼
	public FranchiseeOwner selectFranchiseeOwner(String ownerNo);
	// 가맹점 썸네일 사진 조회
	public List<FranchiseePic> selectFranchiseeThumbnail();
	// 가맹점 사진 삭제
	public int deleteFranchiseePic(int picNo);
	// 사진 번호로 가맹점 사진 하나 조회
	public FranchiseePic selectFranchiseePicOne(int picNo);
	// 가맹점 pc 사양정보 수정
	public int updateFranchiseeSpec(FranchiseeSpec franchiseeSpec);
	// QnA 등록 
	public int insertFranchiseeQnA(FranchiseeQnA franchiseeQnA);
	// pc 사양 조회
	public List<Spec> selectSpec(String specType);
	// FAQ 리스트 카운트 조회
	public int selectFranchiseeFAQCount(String searchWord);
	// FAQ 리스트 출력
	public List<FranchiseeFAQ> selectFranchiseeFAQ(FranchiseeFAQPage franchiseeFAQpage);
	// 가맹점 사진 조회, 저장된 파일 이름, 확장명, 원래 파일 이름, 가맹점 번호 조회
	public List<FranchiseePic> selectFranchiseePic(String franchiseeNo);
	// 가맹점 pc사양 조회
	public FranchiseeSpec selelctFranchiseeSpec(String franchiseeNo);
	// 가맹점 pc사양 입력
	public int insertFranchiseeSpec(FranchiseeSpec franchiseeSpec);
	// 가맹점 사진 입력
	public int insertFranchiseePic(FranchiseePic franchiseePic);
	// 가맹점 좌석 삭제
	public int deleteSeat(String franchiseeNo);
	// 가맹점 좌석정보 조회
	public List<Seat> selectSeat(String franchiseeNo);
	// 가맹점 상세정보 조회
	public Franchisee selectFranchiseeOne(String franchiseeNo);
	// 로그인한 점주의 가맹점 리스트 조회
	public List<Franchisee> selectFranchiseeList(String ownerNo);
	// 좌석 입력
	public int insertFranchiseeSeat(Seat seat);
	// 가맹점 신청
	public int insertUnverifiedFranchisee(Franchisee franchisee);
	// 마지막 가맹점번호 조회
	public String selectFranchiseeSeq();
	// 마지막 가맹점번호 갱신
	public int updateFranchiseeSeq(String nextNo);
	
}
