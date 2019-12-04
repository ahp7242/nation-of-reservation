package com.picaboo.nor.membership.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.picaboo.nor.membership.service.MembershipService;

@RestController
public class MembershipRestController {
	@Autowired private MembershipService membershipService;
	
	@PostMapping("/overlap")
	public int overlapCustomerId(String customerId) {
		System.out.println("restcontroller"+customerId);
		int count = membershipService.getOverlapCustomerId(customerId);
		System.out.println("count" + count);
		return count;
	}
}
