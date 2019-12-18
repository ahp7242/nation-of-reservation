package com.picaboo.nor.membership.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.picaboo.nor.membership.mapper.MembershipMapper;
import com.picaboo.nor.membership.vo.Address;
import com.picaboo.nor.membership.vo.Login;
import com.picaboo.nor.membership.vo.Membership;
import com.picaboo.nor.membership.vo.SignForm;


@Service
@Transactional
public class MembershipServiceImpl implements MembershipService{
	@Autowired private MembershipMapper membershipMapper;
	
	//회원가입정보를 입력하는 서비스
	@Override
	public void addMembership(SignForm signForm) {
		// signForm 을 address, membership 으로 나누어 처리
		
		// 1. address 
		Address address = new Address();
		address.setDetailAddress(signForm.getDetailAddress());
		address.setJibunAddress(signForm.getJibunAddress());
		address.setPostcode(signForm.getPostcode());
		address.setRoadAddress(signForm.getRoadAddress());
		
		// db에 저장하고 기본키 리턴
		membershipMapper.insertAddress(address);
		
		// 2. membership
		
		// 회원번호 생성 코드
		
		// 회원가입후 마지막 등록된 회원번호를 호출
		String seqNo = membershipMapper.selectCustomerSeq();
		// 값이 없을경우 값을 생성함
		if(seqNo == null) {
			seqNo = String.format("%07d", 0);
		}
		
		//String 타입의 seqNo값을 int타입으로 변환후 1을 증가
		String nextNo = String.format("%07d", (Integer.parseInt(seqNo)+1));
		membershipMapper.updateCustomerSeq(nextNo);
		
		//고객의 타입과 번호를 더하여 customerNo값 생성
		String customerNo = signForm.getCustomerType() + nextNo;
		
		System.out.println("cutomerNo: " + customerNo);
		
		Membership membership = new Membership();
		System.out.println("address.getAddressNo: " + address.getAddressNo());
		membership.setAddressNo(address.getAddressNo());
		membership.setCustomerNo(customerNo);
		membership.setCustomerId(signForm.getCustomerId());
		membership.setCustomerPw(signForm.getCustomerPw());
		membership.setCustomerName(signForm.getCustomerName());
		membership.setCustomerBirth(signForm.getCustomerBirth());
		membership.setCustomerGender(signForm.getCustomerGender());
		membership.setCustomerEmail(signForm.getCustomerEmail());
		membership.setCustomerPhone(signForm.getCustomerPhone());
		membership.setCustomerType(signForm.getCustomerType());

		membershipMapper.insertMembership(membership);
		
	}
	
	//로그인시 회원정보의 유무를 확인하는 서비스
	@Override
	public Membership loginMembership(Login login) {
		
		return membershipMapper.login(login);
	}
	
	//회원의 상세정보를 가져오는 서비스
	@Override
	public SignForm detailMembership(String customerNo) {
		//System.out.println("service customerNo:" + customerNo);
		
		//회원정보
		Membership membership = membershipMapper.selectMembershipOne(customerNo);
		System.out.println("servicce membership" + membership);
		SignForm signForm = new SignForm();
		
		signForm.setCustomerNo(customerNo);
		signForm.setCustomerBirth(membership.getCustomerBirth());
		signForm.setCustomerEmail(membership.getCustomerEmail());
		signForm.setCustomerId(membership.getCustomerId());
		signForm.setCustomerName(membership.getCustomerName());
		signForm.setCustomerNo(membership.getCustomerNo());
		signForm.setCustomerPhone(membership.getCustomerPhone());
			
		//주소정보
		Address address = membershipMapper.selectAddress(membership.getAddressNo());	
		System.out.println("service address" + address);
		signForm.setAddressNo(membership.getAddressNo());
		signForm.setDetailAddress(address.getDetailAddress());
		signForm.setRoadAddress(address.getRoadAddress());
		signForm.setJibunAddress(address.getJibunAddress());
		signForm.setPostcode(address.getPostcode());		
						
		return signForm;
	}
	
	//회원의 상세정보를 수정하는 서비스
	@Override
	public void modifyMembership(SignForm signForm) {
		Membership membership = new Membership();
		Address address = new Address();
		
		System.out.println("email"+signForm.getCustomerEmail());
		membership.setCustomerNo(signForm.getCustomerNo());
		membership.setCustomerName(signForm.getCustomerName());
		membership.setCustomerEmail(signForm.getCustomerEmail());
		membership.setCustomerPhone(signForm.getCustomerPhone());
		System.out.println("service membership: " + membership);
		
		address.setAddressNo(signForm.getAddressNo());
		address.setRoadAddress(signForm.getRoadAddress());
		address.setJibunAddress(signForm.getJibunAddress());
		address.setPostcode(signForm.getPostcode());
		address.setDetailAddress(signForm.getDetailAddress());
		System.out.println("service address: " + address);
		
		membershipMapper.updateMembership(membership);
		membershipMapper.updateAddress(address);
	}
	
	//회원의 회원탈퇴를 해주는 서비스
	@Override
	public void removeMembership(Membership membership) {
		//System.out.println("service remove"+membership);
		membershipMapper.deleteMembershipQnA(membership.getCustomerNo());
		membershipMapper.insertDelMembership(membership.getCustomerId());
		membershipMapper.deleteMembership(membership.getCustomerNo());
	}
	
	//회원가입시 아이디 중복확인하는 서비스
	@Override
	public int getOverlapCustomerId(String customerId) {
		
		//System.out.println("서비스"+customerId);
		
		return membershipMapper.selectOverlapCustomerId(customerId);
	}
}
