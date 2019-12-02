package com.picaboo.nor.membership.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.picaboo.nor.membership.vo.*;

@Mapper
public interface MembershipMapper {
	public int insertMembership(Membership membership);
	public String selectMaxNo();
	public Membership selectCustomerOne(Login login);
}
