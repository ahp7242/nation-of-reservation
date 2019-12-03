package com.picaboo.nor.membership.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.picaboo.nor.membership.mapper.MembershipMapper;
import com.picaboo.nor.membership.vo.Login;
import com.picaboo.nor.membership.vo.Membership;

@Service
@Transactional
public class MembershipServiceImpl implements MembershipService{
	@Autowired private MembershipMapper membershipMapper;
	
	//회원가입정보를 입력하는 서비스
	@Override
	public int addMembership(Membership membership) {
		String max = membershipMapper.selectMaxNo();
		if(max == null) {
			max = String.format("%07d", 0);
		}
		max = String.format("%07d", (Integer.parseInt(max)+1));
		
		String customerNo = membership.getCustomerType() + max;
		
		System.out.println(customerNo);
		membership.setCustomerNo(customerNo);
		return membershipMapper.insertMembership(membership);
	}
	
	//로그인시 회원정보의 유무를 확인하는 서비스
	@Override
	public Membership loginMembership(Login login) {

		return membershipMapper.login(login);
	}
	
	//회원의 상세정보를 가져오는 서비스
	@Override
	public Membership detailMembership(String customerNo) {
		System.out.println("service customerNo:" + customerNo);
		return membershipMapper.selectMembershipOne(customerNo);
	}
	
	//회원의 상세정보를 수정하는 서비스
	@Override
	public int modifyMembership(Membership membership) {
		System.out.println("service modify membership:"+membership);
		return membershipMapper.updateMEmbership(membership);
	}
}
