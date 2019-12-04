package com.picaboo.nor.customer.controller;
 
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.picaboo.nor.customer.service.*;
import com.picaboo.nor.customer.vo.*;

@Controller
public class CustomerController {
	@Autowired private CustomerService customerService;
	
	//가맹점이 등록한 pc방 좌석을 고객이 확인을 하는 페이지로 get 요청
	@GetMapping("/selectCustomerSeat")
	public String customer(HttpSession session, Model model) {
		//세션값이 없으면 기본 인덱스로 이동
		if (session.getAttribute("memberNo") == null) {
			return "redirect:/";
		}
		model.addAttribute("memberName",session.getAttribute("memberName"));
		//수정필요 가짜 데이터
		String franchiseeNo = "1";
		List<Seat> seat = customerService.getSeat(franchiseeNo);
		
		model.addAttribute("seat", seat);
		//System.out.println("cnt :"+seat);
		return "customer/selectCustomerSeat";
	}
	
	//기본 인덱스로 get요청
	@GetMapping({"/","/index"})
	public String index(HttpSession session, Model model) {
		return "index";
	}
	
	//로그인후 고객이 보는 인덱스로 get요청
	@GetMapping("/customerIndex")
	public String customerindex(HttpSession session, Model model) {
		if (session.getAttribute("memberNo") == null) {
			return "redirect:/";
		}
		
		System.out.println("커스텀인덱스 세션"+session.getAttribute("memberName"));
		model.addAttribute("memberName",session.getAttribute("memberName"));
		
		return "customerIndex";
	}
}
