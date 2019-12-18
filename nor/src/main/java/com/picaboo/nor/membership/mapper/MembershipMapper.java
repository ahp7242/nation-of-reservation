package com.picaboo.nor.membership.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.picaboo.nor.membership.vo.*;

@Mapper
public interface MembershipMapper {
	//회원가입매퍼
	public int insertMembership(Membership membership);
	//회원번호의 최고값을 불러오는 매퍼
	public String selectCustomerSeq();
	//회원가입시 마지막 고객 번호를 업데이트하는 매퍼
	public int updateCustomerSeq(String customer_no);
	//로그인시 정보의 유무를 확인하는 매퍼
	public Membership login(Login login);
	//고객의 상세정보를 불러오는 매퍼
	public Membership selectMembershipOne(String customerNo);
	//고객의 상세정보를 수정하는 매퍼
	public int updateMembership(Membership membership);
	//고객의 주소를 수정는 매퍼
	public int updateAddress(Address address);
	//고객 회원탈퇴시 회원이 등록한 qna리스트를 삭제
	public int deleteMembershipQnA(String customerNo);
	//고객 회원탈퇴시 회원정보를삭제하느 매퍼
	public int deleteMembership(String customerNo);
	//고개 회원탈퇴시 회원정보를 다른 테이블로 입력한 매퍼
	public int insertDelMembership(String customerId);
	//회원가입시 고객 아이디 중복확인
	public int selectOverlapCustomerId(String customerId);
	//회원가입시 고객의 주소 등록하는 매퍼
	public int insertAddress(Address address);
	//회원정보에 주소정보 표시
	public Address selectAddress(int addressNo);
}
