package com.picaboo.nor.franchisee.service;

import java.util.Map;

import com.picaboo.nor.franchisee.vo.Franchisee;

public interface FranchiseeService {
	
	// 좌석 입력
	public int addFranchiseeSeat(Map<String, String> seatMap);
	// 가맹점 신청
	public int addFranchisee(Franchisee franchisee);
	
}
