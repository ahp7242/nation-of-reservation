package com.picaboo.nor.customer.service;

import java.util.List;

import com.picaboo.nor.customer.vo.Seat;

public interface CustomerService {
	//데이터베이스의 저장된 좌석 가져오는 서비스
	public List<Seat> getSeat(String franchiseeNo);
}
