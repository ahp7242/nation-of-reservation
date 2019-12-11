package com.picaboo.nor.franchisee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.picaboo.nor.franchisee.vo.Franchisee;
import com.picaboo.nor.franchisee.vo.FranchiseeFAQ;
import com.picaboo.nor.franchisee.vo.FranchiseeFAQPage;
import com.picaboo.nor.franchisee.vo.FranchiseeInfoForm;
import com.picaboo.nor.franchisee.vo.FranchiseePic;
import com.picaboo.nor.franchisee.vo.FranchiseeQnA;
import com.picaboo.nor.franchisee.vo.FranchiseeSpec;
import com.picaboo.nor.franchisee.vo.Seat;
import com.picaboo.nor.franchisee.vo.Spec;

@Mapper
public interface FranchiseeMapper {
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
	public int insertFranchisee(Franchisee franchisee);
	// 마지막 가맹점번호 조회
	public String selectFranchiseeSeq();
	// 마지막 가맹점번호 갱신
	public int updateFranchiseeSeq(String nextNo);
	
}
