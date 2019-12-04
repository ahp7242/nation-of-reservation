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
		String seqNo = membershipMapper.selectCustomerSeq();
		if(seqNo == null) {
			seqNo = String.format("%07d", 0);
		}
		String nextNo = String.format("%07d", (Integer.parseInt(seqNo)+1));
		membershipMapper.updateCustomerSeq(nextNo);
		
		String customerNo = membership.getCustomerType() + nextNo;
		
		System.out.println(customerNo);
		membership.setCustomerNo(customerNo);
		return membershipMapper.insertMembership(membership);
	}
	
	//로그인시 회원정보의 유무를 확인하는 서비스
	@Override
	public Membership loginMembership(Login login) {
		Membership membership = membershipMapper.login(login);
		String franchiseeNo = membershipMapper.selectFranchiseeNo(membership.getCustomerNo());
		membership.setFranchiseeNo(franchiseeNo);
		
		return membership;
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
		return membershipMapper.updateMembership(membership);
	}
	
	//회원의 회원탈퇴를 해주는 서비스
	@Override
	public void removeMembership(Membership membership) {
		System.out.println("service remove"+membership);
		
		membershipMapper.insertDelMembership(membership.getCustomerId());
		membershipMapper.deleteMembership(membership.getCustomerNo());
	}
	
	//회원가입시 아이디 중복확인하는 서비스
	@Override
	public int getOverlapCustomerId(String customerId) {
		
		return membershipMapper.selectOverlapCustomerId(customerId);
	}
}
