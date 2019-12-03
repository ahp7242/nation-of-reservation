package com.picaboo.nor.membership.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.picaboo.nor.membership.vo.*;

@Mapper
public interface MembershipMapper {
	//회원가입매퍼
	public int insertMembership(Membership membership);
	//회원번호의 최고값을 불러오는 매퍼
	public String selectMaxNo();
	//로그인시 정보의 유무를 확인하는 매퍼
	public Membership login(Login login);
	//고객의 상세정보를 불러오는 매퍼
	public Membership selectMembershipOne(String customerNo);
	//고객의 상세정보를 수정하는 매퍼
	public int updateMEmbership(Membership membership);
}
