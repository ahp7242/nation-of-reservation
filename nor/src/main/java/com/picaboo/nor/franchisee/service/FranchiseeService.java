package com.picaboo.nor.franchisee.service;

import java.util.List;
import java.util.Map;

import com.picaboo.nor.franchisee.vo.Franchisee;

public interface FranchiseeService {
	
	// 가맹점 상세정보 조회
	public Franchisee getFranchiseeOne(String franchiseeNo); 
	// 가맹점 리스트 조회
	public List<Franchisee> getFranchiseeList(String ownerNo);
	// 좌석 입력
	public int addFranchiseeSeat(Map<String, String> seatMap);
	// 가맹점 신청
	public int addFranchisee(Franchisee franchisee);
	
}
