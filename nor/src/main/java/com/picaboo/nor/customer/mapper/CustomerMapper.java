package com.picaboo.nor.customer.mapper;

import java.util.*;
import org.apache.ibatis.annotations.Mapper;
import com.picaboo.nor.customer.vo.*;

@Mapper
public interface CustomerMapper {
	//좌석 출력
	public List<Seat> selectSeat(String franchiseeNo);
	public List<Franchisee> selectFranchiseeNo();
	public Franchisee selectFranchisee(String franchiseeNo);
}
