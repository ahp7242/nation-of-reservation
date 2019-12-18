package com.picaboo.nor.membership.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.picaboo.nor.membership.service.MembershipService;
import com.picaboo.nor.membership.vo.*;

@Controller
@Transactional
public class MembershipController {
	@Autowired private MembershipService membershipService;
	
	//회원가입시 회원의 타입을 결정할 페이지로 get 요청
	@GetMapping("/signupType")
	public String singupType(HttpSession session) {
		// System.out.println("membershipcontroller session : " + session.getAttribute("memberNo"));
		
		// 세션에서 memberNo값 검사
		if (session.getAttribute("memberName") != null) {
			String type = (String)session.getAttribute("memberType");
			//System.out.println("membershipcontroller type : " + type);
			
			// 세션의 memberType값을 검사하여 각각의 타입으로 분기
			switch(type){
				case "N":
 				case "C":
					//일반 고객페이지로 이동
					return "redirect:/customerIndex";
				case "O":
					//점주 페이지로 이동
					return "redirect:/franchiseeIndex";
			}
		}
		return "membership/signupType";
	}
	
	//타입 선택후 회원가입 입력 폼을 get요청
	@GetMapping("/signup")
	public String singup(HttpSession session, Model model,@RequestParam("customerType")String customerType) {
		// System.out.println("membershipcontroller session : " + session.getAttribute("memberNo"));
		
		// 세션의 memberNo값 검사
		if (session.getAttribute("memberName") != null) {
			String type = (String)session.getAttribute("memberType");
			//System.out.println("membershipcontroller type : " + type);
			
			// 세션의 memberType값을 검사하여 각각의 타입으로 분기
			switch(type){
				case "N":
				case "C":
					//일반 고객페이지로 이동
					return "redirect:/customerIndex";
				case "O":
					//점주 페이지로 이동
					return "redirect:/franchiseeIndex";
			}
		}
		model.addAttribute("customerType",customerType);
		return "membership/signup";
	}
	//회원가입 입력후 post요청
	@PostMapping("/signup")
	public String singup(SignForm signForm) {
		
		//System.out.println("controller signform"+signForm);			
		System.out.println("signForm: " + signForm);
		
		membershipService.addMembership(signForm);
		return "redirect:/login";
	}
	
	//로그인 폼으로 get요청
	@GetMapping("/login")
	public String login(HttpSession session, Model model) {
		// System.out.println("membershipcontroller session : " + session.getAttribute("memberNo"));
		
		// 세션의 memberNo값 검사
		if (session.getAttribute("memberName") != null) {
			String type = (String)session.getAttribute("memberType");
			//System.out.println("membershipcontroller type : " + type);
			
			// 세션의 memberType값을 검사하여 각각의 타입으로 분기
			switch(type){
				case "N":
				case "C":
					//일반 고객페이지로 이동
					return "redirect:/customerIndex";
				case "O":
					//점주 페이지로 이동
					return "redirect:/franchiseeIndex";
			}
		}
		return "membership/login";
	}
	//로그인 post요청(고객 이름, 타입, 번호, 가맹점 번호 세션 저장)
	@PostMapping("/login")
	public String login(HttpSession session, Login login, Model model) {
		// 로그인시 입력된 아이디와 비밀번호로 등록된 정보를 호출하여 Membership타입에 저장
		Membership member = membershipService.loginMembership(login);
		//System.out.println("login member : "+member);
		
		// 로그인후 반환된 값이 null이 아닐경우
		if (member != null) {
			//System.out.println("customer no:"+member.getCustomerNo());
			String type = member.getCustomerNo().substring(0,1);
			System.out.println("type"+type);
			
			//member에 저장된 값을 세션에 저장
			session.setAttribute("memberEmail", member.getCustomerEmail());
			session.setAttribute("memberName", member.getCustomerName());
			session.setAttribute("memberType", member.getCustomerType());
			session.setAttribute("memberNo", member.getCustomerNo());
			
			// 세션의 memberType값을 검사하여 각각의 타입으로 분기
			switch (type) {
				case "N":
				case "C":
					System.out.println("고객 로그인");
					return "redirect:/customerIndex";
				case "O":
					return "redirect:/franchiseeIndex";
			}
		}
		return "redirect:/login";
	}
	
	//로그아웃 get요청
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		// 세션 초기화
		session.invalidate();
		return "redirect:/";
	}
	
	//고객 상세정보 페이지로 get요청
	@GetMapping("/profile")
	public String profile(HttpSession session, Model model, SignForm signForm) {
		// System.out.println("membershipcontroller session : " + session.getAttribute("memberNo"));
		
		// 세션에서 memberNo값 검사
		if (session.getAttribute("memberNo") == null) {
			return "redirect:/";
		}
		String type = (String)session.getAttribute("memberType");
		//System.out.println(type);
		
		//로그인한 고객의 타입이 검사
		if(type == "N") {
			//System.out.println("Naver로그인");
			return "redirect:/customerIndex";
		}
		String customerNo = (String)session.getAttribute("memberNo");
		//System.out.println("profile customerNo"+customerNo);
		
		// 로그인한 회원의 상세정보를 Membership타입에 저장한다.
		signForm = membershipService.detailMembership(customerNo);
		model.addAttribute("membership", signForm);
		model.addAttribute("memberName",session.getAttribute("memberName"));
		//System.out.println("signformController :" + signForm);
		return "membership/profile";	
	}
	
	//고객 상세정보 수정페이지로 get요청
	@GetMapping("/modifyMembership")
	public String modifyMembership(HttpSession session, Model model) {
		// System.out.println("membershipcontroller session : " + session.getAttribute("memberNo"));
		
		//세션에서 memberNo값 검사
		if (session.getAttribute("memberNo") == null) {
			return "redirect:/";
		}
		String customerNo = (String)session.getAttribute("memberNo");
		//System.out.println("modify customerNo:"+customerNo);
		
		// 로그인한 회원의 상세정보를 Membership타입에 저장한다.
		SignForm signForm = membershipService.detailMembership(customerNo);
		System.out.println(signForm.getAddressNo());
		session.setAttribute("addressNo",signForm.getAddressNo());
		//System.out.println("modify membership:"+membership);
		model.addAttribute("memberName",session.getAttribute("memberName"));
		model.addAttribute("membership", signForm);
		
		return "membership/modifyMembership";
	}
	//고객 상세정보 수정후 post요청
	@PostMapping("/modifyMembership")
	public String modifyMembership(HttpSession session, SignForm singForm) {
		String cusNo = (String)session.getAttribute("memberNo");
		int addressNo = (int) session.getAttribute("addressNo");
		//System.out.println("post :"+ cusNo);
		//System.out.println("수정후 " + singForm);		
		//System.out.println("postMod :"+membership);
		singForm.setCustomerNo(cusNo);
		singForm.setAddressNo(addressNo);
		session.removeAttribute("memberName");
		session.setAttribute("memberName", singForm.getCustomerName());
		membershipService.modifyMembership(singForm);
		//System.out.println("@#@@#"+membershipService.modifyMembership(membership));
		return "redirect:/profile";
	}
	
	//고객 회원탈퇴 get요청
	@GetMapping("/removeMembership")
	public String removeMembership(HttpSession session, @RequestParam("customerId")String customerId) {
		
		//System.out.println("remove : " + customerId);
		Membership membership = new Membership();
		membership.setCustomerId(customerId);
		membership.setCustomerNo((String)session.getAttribute("memberNo"));
		membershipService.removeMembership(membership);
		
		session.invalidate();
		return "redirect:/";
	}
	
	//네이버로그인 콜백 페이지 요청
	@GetMapping("/callback")
	public String callback() {
		return "membership/callback";
	}
	
	
}
