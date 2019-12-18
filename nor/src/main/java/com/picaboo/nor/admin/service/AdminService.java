package com.picaboo.nor.admin.service;

import java.util.Map;

import com.picaboo.nor.admin.vo.AdminFAQ;
import com.picaboo.nor.admin.vo.AdminQnA;
import com.picaboo.nor.admin.vo.FeedBack;

public interface AdminService {
	// 가맹점 신청완료
	public int addFranchisee(String franchiseeNo);
	// 가맹점 리스트 조회
	public Map<String, Object> getUnverifiedFranchiseeList(int currentPage, int rowPerPage);	
	// FAQ 삭제
	public int delFAQ(int faqNo);
	// FAQ 등록
	public int addFAQ(AdminFAQ adminFAQ);
	// FAQ 리스트 조회
	public Map<String, Object> getAdminFAQList(int currentPage, int rowPerPage, String searchWord);
	//QnA 수정하는 서비스1고객
	public int modifyQnACustomer(AdminQnA adminQnA);
	// QnA 상세 조회2고객
	public AdminQnA getQnAOneCustomer(int qnaNo); 
	// QnA 리스트 조회3고객
	public Map<String, Object> getAdminQnACustomer(int currentPage, int rowPerPage);
	//QnA 수정하는 서비스
	public int modifyQnA(AdminQnA adminQnA);
	// QnA 상세 조회
	public AdminQnA getQnAOne(int qnaNo); 
	// QnA 리스트 조회
	public Map<String, Object> getAdminQnA(int currentPage, int rowPerPage);
	
	// FeedBack 등록
	public int addFeedBack(FeedBack feedBack);
	// FeedBack 리스트 조회
	public Map<String, Object> getFeedBackBoardList(int currentPage, int rowPerPage);
	// FeedBack 상세 조회
	public FeedBack getFeedBackBoardListOne(int feedBackBoardNo);
}
