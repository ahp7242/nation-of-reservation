package com.picaboo.nor.franchisee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.picaboo.nor.franchisee.mapper.FranchiseeMapper;
import com.picaboo.nor.franchisee.vo.Seat;

@Controller
public class FranchiessSeatController {
	@Autowired FranchiseeMapper franchiseeMapper;
	
	// 자리 값
	@GetMapping("/addFranchiseeSeat")
	public String addFranchiseeSeat() {
		return "addFranchiseeSeat";
	}
	@PostMapping("/addFranchiseeSeat")
	public String addFranchiseeSeat(Seat seat) {
		franchiseeMapper.insertFranchiseeSeat(seat);
		return "redirect:/";
	}
	
	// 선택한 seat값 받아오기
	@RequestMapping(value="/franchiseeSeat", method=RequestMethod.POST)
    @ResponseBody
    public Object checkTestSave(@RequestParam(value="seatArray[]") List<String> seatArray) {
        System.out.println("=seat=");
        for(String seat : seatArray) {
            System.out.println(seat);
        }
        System.out.println(seatArray);
        return seatArray;
    }
	
	// 가맹점 자리 추가
	@GetMapping("/franchiseeSeatIn") 
	public String franchiseeSeatIn() {
		return "franchiseeSeatIn"; 
	}
	
	// index
	@GetMapping({"/test"})
	   public String test() {	      
	      return "test";
	   }


}
