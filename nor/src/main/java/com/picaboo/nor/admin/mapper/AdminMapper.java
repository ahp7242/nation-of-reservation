package com.picaboo.nor.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.picaboo.nor.admin.vo.AdminFAQ;
import com.picaboo.nor.admin.vo.AdminFAQPage;
import com.picaboo.nor.admin.vo.AdminPage;
import com.picaboo.nor.admin.vo.AdminQnA;
import com.picaboo.nor.admin.vo.AdminQnAPage;
import com.picaboo.nor.admin.vo.FeedBack;
import com.picaboo.nor.admin.vo.FeedBackPage;
import com.picaboo.nor.admin.vo.UnverifiedFranchisee;

@Mapper
public interface AdminMapper {
	
	// QnA 를수정하는 매퍼1 고객
	public int updateQnACustomer(AdminQnA adminQnA);
	// QnA 상세정보 조회2 고객
	public AdminQnA selectQnAOneCustomer(int qnaNo);
	// QnA 리스트 카운트 조회3 고객
	public int selectAdminQnACountCustomer();
	// QnA 리스트 출력4 고객
	public List<AdminQnA> selectAdminQnACustomer(AdminQnAPage adminQnAPage);	
	// 가맹점 신청완료
	public int insertFranchisee(String franchiseeNo);
	// 가맹점 신청완료후 삭제
	public int delFranchisee(String franchiseeNo);
	// 가맹점 신청 리스트 출력
	public List<UnverifiedFranchisee> selectUnverifiedFranchiseeList(AdminPage adminPage);	
	// 가맹점 신청 리스트 카운트 조회
	public int selectFranchiseeCount();
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
	
	// FeedBack 등록
	public int insertFeedBack(FeedBack feedBack);
	// FeedBack 리스트 출력
	public List<FeedBack> selectFeedBackBoardList(FeedBackPage feedBackPage);
	// FeedBack 리스트 카운트 조회
	public int selectFeedBackBoardListCount();
	// FeedBack 리스트 상세정보 조회
	public FeedBack selectFeedBackBoardListOne(int feedBackBoardNo);
}
