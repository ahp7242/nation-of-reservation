package com.picaboo.nor.franchisee.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.picaboo.nor.franchisee.vo.Seat;

@Mapper
public interface FranchiseeMapper {
	
	// 좌석 입력
	public int insertFranchiseeSeat(Seat seat);
}
