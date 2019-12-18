package com.picaboo.nor.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.picaboo.nor.admin.service.AdminService;
import com.picaboo.nor.admin.vo.AdminFAQ;
import com.picaboo.nor.admin.vo.AdminQnA;
import com.picaboo.nor.admin.vo.FeedBack;

@Controller

public class AdminController {
	@Autowired AdminService adminService;
	// 가맹점 등록 완료
	@PostMapping("/addFranchisee")
    public String addFranchisee(@RequestParam(value="franchiseeNo") String franchiseeNo) {
		System.out.println("post요청"+franchiseeNo);
		adminService.addFranchisee(franchiseeNo);
        return "redirect:/unverifiedFranchiseeList";
	}
	
	// 가맹점 리스트 페이지
	@GetMapping("/unverifiedFranchiseeList")
	public String getUnverifiedFranchiseeList(HttpSession session, Model model, 
								@RequestParam(value="currentPage", defaultValue="1") int currentPage){
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		if (ownerNo == null) {
			return "redirect:/";
		}
		//System.out.println("currentPage : " + currentPage);
		
		int rowPerPage = 10;
		Map<String, Object> map = adminService.getUnverifiedFranchiseeList(currentPage, rowPerPage);
		model.addAttribute("map", map);
		//System.out.println("map : " + map);
		return "admin/unverifiedFranchiseeList";
	}
	// FAQ 삭제
	@PostMapping("/delFAQ")
    public String delFAQ(HttpSession session,@RequestParam(value="qnaNo") int faqNo ) {
		adminService.delFAQ(faqNo);
		System.out.println("faqNo"+faqNo);
        return "redirect:/FAQAdmin";
	}
			
	// FAQ 등록
	@PostMapping("/addFAQ")
    public String addFAQ(AdminFAQ adminFAQ, HttpSession session) {
		adminService.addFAQ(adminFAQ);
        return "redirect:/FAQAdmin";
	}
		
	// FAQ 리스트 페이지
	@GetMapping("/FAQAdmin")
	public String getAdminFAQList(HttpSession session, Model model, 
								@RequestParam(value="currentPage", defaultValue="1") int currentPage,
								@RequestParam(value="searchWord", required = false) String searchWord){
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		if (ownerNo == null) {
			return "redirect:/";
		}
		//System.out.println("currentPage : " + currentPage);
		//System.out.println("searchWord : " + searchWord);
		int rowPerPage = 10;
		Map<String, Object> map = adminService.getAdminFAQList(currentPage, rowPerPage, searchWord);
		model.addAttribute("map", map);
		//System.out.println("map : " + map);
		return "admin/FAQAdmin";
	}
		
	//QnA 답변 post요청 고객쪽0
	@PostMapping("/QnAUpdateCustomer")
	public String modifyQnACustomer(AdminQnA adminQnA) {
		System.out.println("수정후 " + adminQnA);
		adminService.modifyQnACustomer(adminQnA);
		return "redirect:/QnAListCustomer";
	}
		
	// QnA 상세 페이지 요청 고객쪽1
	@GetMapping("/QnADetailCustomer")
	public String detailQnACustomer(HttpSession session, @RequestParam(value="qnaNo") int qnaNo, Model model) {	
		//System.out.println("QnADetail Get 요청");
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		if (ownerNo == null) {
			return "redirect:/";
		}
		// 가맹점 좌석정보 가져와서 넘김
		AdminQnA adminQnA = adminService.getQnAOneCustomer(qnaNo);
		
		model.addAttribute("adminQnA", adminQnA);

		return "admin/QnADetailCustomer";
	}
		
	// QnA 리스트 페이지 고객쪽3
	@GetMapping("/QnAListCustomer")
	public String getQnAListCustomer(HttpSession session, Model model, 
								@RequestParam(value="currentPage", defaultValue="1") int currentPage
								){
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		if (ownerNo == null) {
			return "redirect:/";
		}
		//System.out.println("currentPage : " + currentPage);
		int rowPerPage = 10;
		Map<String, Object> map = adminService.getAdminQnACustomer(currentPage, rowPerPage);
		model.addAttribute("map", map);
		//System.out.println("map : " + map);
		//System.out.println("map list: " + map.get("list"));
		return "admin/QnAListCustomer";
	}	
	
	
	//QnA 답변 post요청
	@PostMapping("/QnAUpdate")
	public String modifyQnA(AdminQnA adminQnA) {
		System.out.println("수정후 " + adminQnA);
		adminService.modifyQnA(adminQnA);
		return "redirect:/QnAListCustomer";
	}
		
	// QnA 상세 페이지 요청
	@GetMapping("/QnADetail")
	public String detailQnA(HttpSession session, @RequestParam(value="qnaNo") int qnaNo, Model model) {	
		//System.out.println("QnADetail Get 요청");
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		if (ownerNo == null) {
			return "redirect:/";
		}
		// 가맹점 좌석정보 가져와서 넘김
		AdminQnA adminQnA = adminService.getQnAOne(qnaNo);
		
		model.addAttribute("adminQnA", adminQnA);

		return "admin/QnADetail";
	}
		
	// QnA 리스트 페이지
	@GetMapping("/QnAList")
	public String getQnAList(HttpSession session, Model model, 
								@RequestParam(value="currentPage", defaultValue="1") int currentPage
								){
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		if (ownerNo == null) {
			return "redirect:/";
		}
		//System.out.println("currentPage : " + currentPage);
		int rowPerPage = 10;
		Map<String, Object> map = adminService.getAdminQnA(currentPage, rowPerPage);
		model.addAttribute("map", map);
		//System.out.println("map : " + map);
		//System.out.println("map list: " + map.get("list"));
		return "admin/QnAList";
	}
	
	//Admin index 요청
	@GetMapping("/adminIndex")
    public String adminIndex(HttpSession session){
		if (session.getAttribute("memberNo") == null) {
         return "redirect:/";
		}
      return "adminIndex";
	}
	
	// FeedBack 등록 페이지 요청
	@GetMapping("/addFeedBack")
	public String addFeedBack() {
		System.out.println("feedback get요청");
		return "admin/addFeedBack";
	}
	
	// FeedBack 등록
	@PostMapping("/addFeedBack")
	public String addFeedBack(FeedBack feedBack) {
		adminService.addFeedBack(feedBack);
		System.out.println("feedback 등록완료");
		return "redirect:/getFeedBackBoardList";
	}
	
	// FeedBack 리스트 페이지
	@GetMapping("/getFeedBackBoardList")
	public String getFeedBackBoardList(Model model, @RequestParam(value="currentPage", defaultValue="1") int currentPage){
		// System.out.println("currentPage : " + currentPage);
		int rowPerPage = 10;
		Map<String, Object> map = adminService.getFeedBackBoardList(currentPage, rowPerPage);
		model.addAttribute("map", map);
		// System.out.println("map : " + map);
		// System.out.println("map list: " + map.get("list"));
		return "admin/getFeedBackBoardList";
	}
	
	// FeedBack 상세 페이지 요청
	@GetMapping("/detailFeedBack")
	public String detailFeedBack(Model model, @RequestParam(value="feedBackBoardNo") int feedBackBoardNo) {	
		System.out.println("QnADetail Get 요청");
		System.out.println("no" + feedBackBoardNo);
		FeedBack feedBack = adminService.getFeedBackBoardListOne(feedBackBoardNo);
		
		model.addAttribute("feedBack", feedBack);

		return "admin/detailFeedBack";
	}

}
