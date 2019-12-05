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
	
	//인덱스에서 출력할 프렌차이즈 목록을 List로 반환
	@PostMapping("/getFranchiseeList")
	public List<Franchisee> getFranchiseeList(){
		List<Franchisee> list = customerService.getFranchiseeNo();
		System.out.println("restcontroller"+list);
		return list;
	}
}
