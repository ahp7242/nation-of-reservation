package com.picaboo.nor.customer.mapper;

import java.util.*;
import org.apache.ibatis.annotations.Mapper;
import com.picaboo.nor.customer.vo.*;

@Mapper
public interface CustomerMapper {
	//좌석 출력
	public List<Seat> selectSeat(String franchiseeNo);
	//모든 프렌차이즈 정보를 가져옴
	public List<Franchisee> selectFranchiseeNo();
	//프렌차이즈 상세정보를 가져옴
	public Franchisee selectFranchisee(String franchiseeNo);
}
