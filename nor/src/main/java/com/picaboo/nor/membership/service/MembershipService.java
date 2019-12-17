package com.picaboo.nor.membership.service;

import com.picaboo.nor.membership.vo.*;

public interface MembershipService {
	//회원가입정보를 입력하는 서비스
	public void addMembership(SignForm signForm);
	//로그인시 회원정보의 유무를 확인하는 서비스
	public Membership loginMembership(Login login);
	//회원의 상세정보를 가져오는 서비스
	public SignForm detailMembership(String customerNo);
	//회원의 상세정보를 수정하는 서비스
	public void modifyMembership(SignForm singForm);
	//회원탈퇴 서비스
	public void removeMembership(Membership membership);
	//회원가입시 아이디 중복확인
	public int getOverlapCustomerId(String customerId);

}
