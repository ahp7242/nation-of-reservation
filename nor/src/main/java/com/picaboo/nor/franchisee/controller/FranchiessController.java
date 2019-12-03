package com.picaboo.nor.franchisee.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.picaboo.nor.franchisee.service.FranchiseeService;

@Controller
public class FranchiessController {
	@Autowired FranchiseeService franchiseeService;
	
	@GetMapping("/addFranchisee")
	public String addFranchisee() {
		return "addFranchisee";
	}
	
	// 가맹점 좌석 입력 요청
	@PostMapping("/addFranchiseeSeat")
	public String addFranchiseeSeat( @RequestParam() HashMap<String,String> seatMap) {
		System.out.println("addFranchiseeSeat Post 요청");
		
		System.out.println("addFranchiseeSeat.seatList: " + seatMap);
		franchiseeService.addFranchiseeSeat(seatMap);
		
		return "addFranchiseeSeat";
	}
	
	// 가맹점 좌석 추가 페이지 요청
	@GetMapping("/addFranchiseeSeat") 
	public String addFranchiseeSeat() {
		System.out.println("addFranchiseeSeat Get 요청");
		return "addFranchiseeSeat";
	}
	
	// index
	@GetMapping({"/test"})
	   public String test() {	      
	      return "test";
	   }


}
