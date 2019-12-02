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
	
	@GetMapping("/singupType")
	public String singupType() {
		return "singupType";
	}
	@GetMapping("/singup")
	public String singup(Model model,@RequestParam("customerType")String customerType) {
		model.addAttribute("customerType",customerType);
		return "singup";
	}
	@PostMapping("/singup")
	public String singup(Membership membership) {
		
		System.out.println(membership);
		membershipService.addMembership(membership);
		
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@PostMapping("/login")
	public String login(HttpSession session, Login login) {
		Membership member = membershipService.loginMembership(login);
		System.out.println("로그인 멤버"+member);
		session.setAttribute("memberName", member.getCustomerName());
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
