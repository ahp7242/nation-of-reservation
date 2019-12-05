package com.picaboo.nor.franchisee.service;

import java.util.List;
import java.util.Map;

import com.picaboo.nor.franchisee.vo.Franchisee;
import com.picaboo.nor.franchisee.vo.Seat;

public interface FranchiseeService {
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
