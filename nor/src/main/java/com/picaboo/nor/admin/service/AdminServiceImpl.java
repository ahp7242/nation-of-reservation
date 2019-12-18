package com.picaboo.nor.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.picaboo.nor.admin.mapper.AdminMapper;
import com.picaboo.nor.admin.vo.AdminFAQ;
import com.picaboo.nor.admin.vo.AdminFAQPage;
import com.picaboo.nor.admin.vo.AdminPage;
import com.picaboo.nor.admin.vo.AdminQnA;
import com.picaboo.nor.admin.vo.AdminQnAPage;
import com.picaboo.nor.admin.vo.FeedBack;
import com.picaboo.nor.admin.vo.FeedBackPage;
import com.picaboo.nor.admin.vo.UnverifiedFranchisee;


@Service
@Transactional
public class AdminServiceImpl implements AdminService{
	@Autowired AdminMapper adminMapper;
	@Autowired JavaMailSender javaMailSender;
	
	// admin 답변기능
	@Override
	public int modifyQnACustomer(AdminQnA adminQnA) {
		String email = adminQnA.getCustomerMail();
		String text = adminQnA.getQnaAnswer();

		System.out.println("service IMPL EMAI:  "+email);
		System.out.println("service IMPL TEXT:  "+text);
		if(email.length() > 10) {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
				simpleMailMessage.setTo(email);
				simpleMailMessage.setFrom("kuooeee@gmail.com");
				simpleMailMessage.setSubject("PI-KA-BOO 문의하신 답변내용이 전달되었습니다.");
				simpleMailMessage.setText(text);
				javaMailSender.send(simpleMailMessage);
		}
		return adminMapper.updateQnACustomer(adminQnA);
	}
	
	// QnA 상세정보 조회
	@Override
	public AdminQnA getQnAOneCustomer(int qnaNo) {
		System.out.println("ServiceImpl.getFranchiseeOne franchiseeNo: " + qnaNo);
		return adminMapper.selectQnAOneCustomer(qnaNo);
	}
		
	// QnA 리스트 조회
	@Override
	public Map<String, Object> getAdminQnACustomer(int currentPage, int rowPerPage) {
		
		// 페이징 코드
		// Mapper로 페이징 정보를 넘기기 위해 VO에 값 저장
		AdminQnAPage adminQnAPage = new AdminQnAPage();
		adminQnAPage.setRowPerPage(rowPerPage);
		adminQnAPage.setBeginRow((currentPage-1)*rowPerPage);
		
		List<AdminQnA> qnaList = adminMapper.selectAdminQnACustomer(adminQnAPage);
		System.out.println("serviceImpl List: "+qnaList);
		
		// 페이징 버튼을 위한 마지막 페이지 계산
		int totalRowCount = adminMapper.selectAdminQnACountCustomer();
		int lastPage = 0;
		if(totalRowCount % rowPerPage == 0) {
			lastPage = totalRowCount / rowPerPage;
		} else {
			lastPage = totalRowCount / rowPerPage + 1;
		}
		
		// 페이징한 리스트와 현재 페이지 정보를 맵에 저장하여 리턴
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", qnaList);
		map.put("currentPage", currentPage);
		map.put("totalRowCount", totalRowCount);
		map.put("lastPage", lastPage);
			return map;
		}	
	
	
	//가맹점 신청 완료
	@Override
	public int addFranchisee(String franchiseeNo) {	
		//System.out.println("삭제할 가맹점번호 Impl");
		adminMapper.insertFranchisee(franchiseeNo);
		System.out.println("삭제할 가맹점번호 Impl"+franchiseeNo);
		return adminMapper.delFranchisee(franchiseeNo);
	}
	
	// 가맹점 리스트 조회
	@Override
	public Map<String, Object> getUnverifiedFranchiseeList(int currentPage, int rowPerPage) {
		
		// 페이징 코드, 검색어 입력
		// Mapper로 페이징 정보를 넘기기 위해 VO에 값 저장
		AdminPage adminPage = new AdminPage();
		adminPage.setRowPerPage(rowPerPage);
		adminPage.setBeginRow((currentPage-1)*rowPerPage);
		
		List<UnverifiedFranchisee> list = adminMapper.selectUnverifiedFranchiseeList(adminPage);
		System.out.println("serviceImpl List: "+list);
		
		// 페이징 버튼을 위한 마지막 페이지 계산
		int totalRowCount = adminMapper.selectFranchiseeCount();
		int lastPage = 0;
		if(totalRowCount % rowPerPage == 0) {
			lastPage = totalRowCount / rowPerPage;
		} else {
			lastPage = totalRowCount / rowPerPage + 1;
		}
		
		// 검색과, 페이징한 리스트와 현재 페이지 정보를 맵에 저장하여 리턴
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("currentPage", currentPage);
		map.put("totalRowCount", totalRowCount);
		map.put("lastPage", lastPage);
		return map;
	}	
	
	// FAQ삭제
	@Override
	public int delFAQ(int faqNo) {		
		return adminMapper.deleteFAQ(faqNo);
	}
	// FAQ 등록
	@Override
	public int addFAQ(AdminFAQ adminFAQ) {		
		return adminMapper.insertFAQ(adminFAQ);
	}
	
	// FAQ 리스트 조회
	@Override
	public Map<String, Object> getAdminFAQList(int currentPage, int rowPerPage, String searchWord) {
		
		// 페이징 코드, 검색어 입력
		// Mapper로 페이징 정보를 넘기기 위해 VO에 값 저장
		AdminFAQPage adminFAQPage = new AdminFAQPage();
		adminFAQPage.setRowPerPage(rowPerPage);
		adminFAQPage.setBeginRow((currentPage-1)*rowPerPage);
		adminFAQPage.setSearchWord(searchWord);
		List<AdminFAQ> list = adminMapper.selectAdminFAQ(adminFAQPage);
		System.out.println("serviceImpl List: "+list);
		System.out.println("IMPL 검색: "+searchWord);
		
		// 페이징 버튼을 위한 마지막 페이지 계산
		int totalRowCount = adminMapper.selectAdminFAQCount(searchWord);
		int lastPage = 0;
		if(totalRowCount % rowPerPage == 0) {
			lastPage = totalRowCount / rowPerPage;
		} else {
			lastPage = totalRowCount / rowPerPage + 1;
		}
		
		// 검색과, 페이징한 리스트와 현재 페이지 정보를 맵에 저장하여 리턴
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("currentPage", currentPage);
		map.put("totalRowCount", totalRowCount);
		map.put("lastPage", lastPage);
		map.put("searchWord", searchWord);
		return map;
	}
	
	// admin 답변기능
	@Override
	public int modifyQnA(AdminQnA adminQnA) {
		String email = adminQnA.getCustomerMail();
		String text = adminQnA.getQnaAnswer();

		System.out.println("service IMPL EMAI:  "+email);
		System.out.println("service IMPL TEXT:  "+text);
		if(email.length() > 10) {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
				simpleMailMessage.setTo(email);
				simpleMailMessage.setFrom("kuooeee@gmail.com");
				simpleMailMessage.setSubject("PI-KA-BOO 문의하신 답변내용이 전달되었습니다.");
				simpleMailMessage.setText(text);
				javaMailSender.send(simpleMailMessage);
		}
		return adminMapper.updateQnA(adminQnA);
	}
	
	// QnA 상세정보 조회
	@Override
	public AdminQnA getQnAOne(int qnaNo) {
		System.out.println("ServiceImpl.getFranchiseeOne franchiseeNo: " + qnaNo);
		return adminMapper.selectQnAOne(qnaNo);
	}
		
	// QnA 리스트 조회
	@Override
	public Map<String, Object> getAdminQnA(int currentPage, int rowPerPage) {
		
		// 페이징 코드
		// Mapper로 페이징 정보를 넘기기 위해 VO에 값 저장
		AdminQnAPage adminQnAPage = new AdminQnAPage();
		adminQnAPage.setRowPerPage(rowPerPage);
		adminQnAPage.setBeginRow((currentPage-1)*rowPerPage);
		
		List<AdminQnA> qnaList = adminMapper.selectAdminQnA(adminQnAPage);
		System.out.println("serviceImpl List: "+qnaList);
		
		// 페이징 버튼을 위한 마지막 페이지 계산
		int totalRowCount = adminMapper.selectAdminQnACount();
		int lastPage = 0;
		if(totalRowCount % rowPerPage == 0) {
			lastPage = totalRowCount / rowPerPage;
		} else {
			lastPage = totalRowCount / rowPerPage + 1;
		}
		
		// 페이징한 리스트와 현재 페이지 정보를 맵에 저장하여 리턴
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", qnaList);
		map.put("currentPage", currentPage);
		map.put("totalRowCount", totalRowCount);
		map.put("lastPage", lastPage);
			return map;
		}
	
	// FeedBack 등록
	@Override
	public int addFeedBack(FeedBack feedBack) {
		System.out.println(feedBack);
		System.out.println("impl");
		return adminMapper.insertFeedBack(feedBack);
	}
	
	// FeedBack 리스트 조회
	@Override
	public Map<String, Object> getFeedBackBoardList(int currentPage, int rowPerPage) {
		// 페이징
		FeedBackPage feedBackPage = new FeedBackPage();
		feedBackPage.setRowPerPage(rowPerPage);
		feedBackPage.setBeginRow((currentPage-1)*rowPerPage);
		
		List<FeedBack> feedBackBoardList = adminMapper.selectFeedBackBoardList(feedBackPage);
		System.out.println("serviceImpl List : " + feedBackBoardList);
		
		// 페이징 처음 마지막 계산
		int totalRowCount = adminMapper.selectFeedBackBoardListCount();
		int lastPage = 0;
		if(totalRowCount % rowPerPage == 0) {
			lastPage = totalRowCount / rowPerPage;
		} else {
			lastPage = totalRowCount / rowPerPage + 1;
		}
		// 페이징한 리스트와 현재 페이지 정보를 맵에 저장하여 리턴
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", feedBackBoardList);
		map.put("currentPage", currentPage);
		map.put("totalRowCount", totalRowCount);
		map.put("lastPage", lastPage);
		
		return map;
	}
	
	// FeedBack 상세정보 조회
	@Override
	public FeedBack getFeedBackBoardListOne(int feedBackBoardNo) {
		System.out.println("ServiceImpl.getFeedBackBoardListOne feedBackBoardNo: " + feedBackBoardNo);
		return adminMapper.selectFeedBackBoardListOne(feedBackBoardNo);
	}
}
