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
	
	@GetMapping("/customer")
	public String customer(Model model) {
		String franchiseeNo = "1";
		List<Seat> seat = customerService.getSeat(franchiseeNo);
		
		model.addAttribute("seat", seat);
		//System.out.println("cnt :"+seat);
		return "customer";
	}
	
	@GetMapping({"/","/index"})
	public String index(HttpSession session, Model model) {
		
		System.out.println("인덱스 세션"+session.getAttribute("memberName"));
		if(session.getAttribute("memberName") != null) {
			model.addAttribute("memberName",session.getAttribute("memberName"));
		}
		return "index";
	}
}
