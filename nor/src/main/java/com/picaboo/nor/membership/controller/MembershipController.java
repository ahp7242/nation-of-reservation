package com.picaboo.nor.membership.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.picaboo.nor.membership.service.MembershipService;
import com.picaboo.nor.membership.vo.*;

@Controller
public class MembershipController {
	@Autowired private MembershipService membershipService;
	
	//회원가입시 회원의 타입을 결정할 페이지로 get 요청
	@GetMapping("/signupType")
	public String singupType(HttpSession session) {
		// System.out.println("membershipcontroller session : " + session.getAttribute("memberNo"));
		if (session.getAttribute("memberNo") != null) {
			String type = (String)session.getAttribute("memberType");
			//System.out.println("membershipcontroller type : " + type);
			switch(type){
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
		if (session.getAttribute("memberNo") != null) {
			String type = (String)session.getAttribute("memberType");
			//System.out.println("membershipcontroller type : " + type);
			switch(type){
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
	public String singup(Membership membership) {
		
		//System.out.println("membershipcontroller membership : "+membership);
		membershipService.addMembership(membership);
		
		return "redirect:/login";
	}
	
	//로그인 폼으로 get요청
	@GetMapping("/login")
	public String login(HttpSession session) {
		// System.out.println("membershipcontroller session : " + session.getAttribute("memberNo"));
		if (session.getAttribute("memberNo") != null) {
			String type = (String)session.getAttribute("memberType");
			//System.out.println("membershipcontroller type : " + type);
			switch(type){
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
		Membership member = membershipService.loginMembership(login);
		if (member != null) {
			//System.out.println("customer no:"+member.getCustomerNo());
			String type = member.getCustomerNo().substring(0,1);
			//System.out.println("type"+type);
			session.setAttribute("memberName", member.getCustomerName());
			session.setAttribute("memberType", member.getCustomerType());
			session.setAttribute("memberNo", member.getCustomerNo());
			switch (type) {
			case "C":
				System.out.println("고객 로그인");
				return "redirect:/customerIndex";
			case "O":
				System.out.println("가맹점 로그인" + member.getFranchiseeNo());
				session.setAttribute("franchiseeNo", member.getFranchiseeNo());
				return "redirect:/franchiseeIndex";
			}
		}
		return "redirect:/login";
	}
	
	//로그아웃 get요청
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	//고객 상세정보 페이지로 get요청
	@GetMapping("/profile")
	public String profile(HttpSession session, Model model) {
		// System.out.println("membershipcontroller session : " + session.getAttribute("memberNo"));
		if (session.getAttribute("memberNo") == null) {
			return "redirect:/";
		}
		String customerNo = (String)session.getAttribute("memberNo");
		//System.out.println("profile customerNo"+customerNo);
		Membership membership = membershipService.detailMembership(customerNo);
		model.addAttribute("membership", membership);
		return "membership/profile";
	}
	
	//고객 상세정보 수정페이지로 get요청
	@GetMapping("/modifyMembership")
	public String modifyMembership(HttpSession session, Model model) {
		// System.out.println("membershipcontroller session : " + session.getAttribute("memberNo"));
		if (session.getAttribute("memberNo") == null) {
			return "redirect:/";
		}
		String customerNo = (String)session.getAttribute("memberNo");
		//System.out.println("modify customerNo:"+customerNo);
		Membership membership = membershipService.detailMembership(customerNo);
		//System.out.println("modify membership:"+membership);
		model.addAttribute("membership", membership);
		
		return "membership/modifyMembership";
	}
	//고객 상세정보 수정후 post요청
	@PostMapping("/modifyMembership")
	public String modifyMembership(Membership membership) {
		System.out.println("수정후 " + membership);
		membershipService.modifyMembership(membership);
		return "redirect:/profile";
	}
	
	//고객 회원탈퇴 get요청
	@GetMapping("/removeMembership")
	public String removeMembership(HttpSession session, @RequestParam("customerId")String customerId) {
		
		System.out.println("remove : " + customerId);
		Membership membership = new Membership();
		membership.setCustomerId(customerId);
		membership.setCustomerNo((String)session.getAttribute("memberNo"));
		membershipService.removeMembership(membership);
		
		session.invalidate();
		return "redirect:/";
	}
}
