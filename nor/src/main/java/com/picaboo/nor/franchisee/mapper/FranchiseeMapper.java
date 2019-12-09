package com.picaboo.nor.franchisee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.picaboo.nor.franchisee.vo.Franchisee;
import com.picaboo.nor.franchisee.vo.FranchiseeSpec;
import com.picaboo.nor.franchisee.vo.FranchiseePic;
import com.picaboo.nor.franchisee.vo.Seat;

@Mapper
public interface FranchiseeMapper {
	// 가맹점 pc사양 입력
	public int insertFranchiseeInfo(FranchiseeSpec franchiseeSpec);
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
