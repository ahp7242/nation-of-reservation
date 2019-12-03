package com.picaboo.nor.franchisee.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.picaboo.nor.franchisee.service.FranchiseeService;
import com.picaboo.nor.franchisee.vo.Franchisee;

@Controller
public class FranchiessController {
	@Autowired FranchiseeService franchiseeService;
	// 가맹점주 페이지 요청
	@GetMapping("/franchiseeIndex")
	public String franchiseeIndex() {
		return "franchiseeIndex";
	}
	
	// 가맹점 등록 페이지 요청
	@GetMapping("/addFranchisee")
	public String addFranchisee() {
		return "franchisee/addFranchisee";
	}
	
	// 가맹점 등록 요청
	@PostMapping("/addFranchisee")
    public String addBoard(Franchisee franchisee) {
        System.out.print(franchisee);
        
		
        franchiseeService.addFranchisee(franchisee);
        //System.out.printf("BoardController.AddBoard : %d 행 입력성공", row);
        
        
        return "redirect:/franchiseeIndex";
	}
	
	// 가맹점 좌석 입력 요청
	@PostMapping("/addFranchiseeSeat")
	public String addFranchiseeSeat( @RequestParam() HashMap<String,String> seatMap) {
		System.out.println("addFranchiseeSeat Post 요청");
		
		System.out.println("addFranchiseeSeat.seatList: " + seatMap);
		//franchiseeService.addFranchiseeSeat(seatMap);
		
		return "franchisee/addFranchiseeSeat";
	}
	
	// 가맹점 좌석 추가 페이지 요청
	@GetMapping("/addFranchiseeSeat") 
	public String addFranchiseeSeat() {
		System.out.println("addFranchiseeSeat Get 요청");
		return "franchisee/addFranchiseeSeat";
	}
	
	// index
	@GetMapping({"/test"})
	   public String test() {	      
	      return "test";
	   }


}
