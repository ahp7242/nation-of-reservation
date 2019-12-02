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
	
	@Override
	public Membership loginMembership(Login login) {

		return membershipMapper.selectCustomerOne(login);
	}
}
