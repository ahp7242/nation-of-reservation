package com.picaboo.nor.customer.controller;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.picaboo.nor.customer.service.CustomerService;
import com.picaboo.nor.customer.vo.Seat;

@Controller
public class CustomerController {
	@Autowired private CustomerService customerService;
	
	@GetMapping("/")
	public String index(Model model) {
		String franchiseeNo = "1";
		List<Seat> seat = customerService.getSeat(franchiseeNo);
		
		model.addAttribute("seat", seat);
		//System.out.println("cnt :"+seat);
		return "customer";
	}
}
