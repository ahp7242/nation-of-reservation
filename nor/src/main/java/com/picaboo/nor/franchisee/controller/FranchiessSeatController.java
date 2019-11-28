package com.picaboo.nor.franchisee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.picaboo.nor.franchisee.mapper.FranchiseeMapper;
import com.picaboo.nor.franchisee.vo.Seat;

@Controller
public class FranchiessSeatController {
	@Autowired FranchiseeMapper franchiseeMapper;
	
	// 자리 값
	@ResponseBody
	@GetMapping("/addFranchiseeSeat")
	public String addFranchiseeSeat() {
		return "addFranchiseeSeat";
	}
	@PostMapping("/addFranchiseeSeat")
	public String addFranchiseeSeat(Seat seat) {
		franchiseeMapper.insertFranchiseeSeat(seat);
		return "redirect:/";
	}
}
