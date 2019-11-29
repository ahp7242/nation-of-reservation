package com.picaboo.nor.franchisee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.picaboo.nor.franchisee.vo.Seat;

@Mapper
public interface FranchiseeMapper {
	public List<Seat> insertFranchiseeSeat(Seat seat);
//	public int insertFranchiseeSeat(Seat seat);
}
