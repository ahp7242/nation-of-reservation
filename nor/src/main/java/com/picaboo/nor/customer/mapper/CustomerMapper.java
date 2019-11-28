package com.picaboo.nor.customer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.picaboo.nor.customer.vo.Seat;

@Mapper
public interface CustomerMapper {
	public List<Seat> selectSeat(String franchiseeNo);
}
