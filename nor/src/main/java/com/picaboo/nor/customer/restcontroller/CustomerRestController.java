package com.picaboo.nor.customer.restcontroller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.picaboo.nor.customer.service.CustomerService;
import com.picaboo.nor.customer.vo.*;

@RestController
public class CustomerRestController {
	@Autowired private CustomerService customerService;
	
	@PostMapping("/getFranchiseeSeatInfo")
	public List<Franchisee> getFranchiseeSeatInfo(){
		List<Franchisee> list = customerService.getFranchiseeNo();
		System.out.println("restcontroller"+list);
		return list;
	}
}
