package com.picaboo.nor.customer.controller;
 
import java.util.List;

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
	public String index() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
