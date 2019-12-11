package com.picaboo.nor.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.picaboo.nor.admin.vo.AdminFAQ;
import com.picaboo.nor.admin.vo.AdminFAQPage;
import com.picaboo.nor.admin.vo.AdminQnA;
import com.picaboo.nor.admin.vo.AdminQnAPage;
import com.picaboo.nor.franchisee.vo.FranchiseeQnA;

@Mapper
public interface AdminMapper {
	// FAQ 삭제 
	public int deleteFAQ(int faqNo);
	// FAQ 등록 
	public int insertFAQ(AdminFAQ adminFAQ);
	// FAQ 리스트 카운트 조회
	public int selectAdminFAQCount(String searchWord);
	// FAQ 리스트 출력
	public List<AdminFAQ> selectAdminFAQ(AdminFAQPage adminFAQpage);
	// QnA 를수정하는 매퍼
	public int updateQnA(AdminQnA adminQnA);
	// QnA 상세정보 조회
	public AdminQnA selectQnAOne(int qnaNo);
	// QnA 리스트 카운트 조회
	public int selectAdminQnACount();
	// QnA 리스트 출력
	public List<AdminQnA> selectAdminQnA(AdminQnAPage adminQnAPage);

}
