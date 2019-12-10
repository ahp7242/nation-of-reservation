 package com.picaboo.nor.customer.restcontroller;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@PostMapping("/seatReservation")
	public void seatReservation(HttpSession session,@RequestParam("seatNo")String seatNo,
								@RequestParam("date")String date,
								@RequestParam("time")String time,
								@RequestParam("franchiseeNo")String franchiseeNo) {
		String memberName = (String) session.getAttribute("memberName");
		String memberNo = (String) session.getAttribute("memberNo");
		
		System.out.println("memberName"+memberName);
		System.out.println("memberNo"+memberNo);
		
		/*
		 * System.out.println("예약컨트롤러"); System.out.println("seatNo"+seatNo);
		 * System.out.println("date"+date); System.out.println("time"+time);
		 * System.out.println("franchiseeNo"+franchiseeNo);
		 * System.out.println(memberName);
		 */
		SeatReservation seatReservation = new SeatReservation();
		seatReservation.setCustomerNo(memberNo);
		seatReservation.setCustomerName(memberName);
		seatReservation.setFranchiseeNo(franchiseeNo);
		seatReservation.setReservationDate(date);
		seatReservation.setReservationTime(time);
		seatReservation.setSeatNo(Integer.parseInt(seatNo));
		seatReservation.setType("R");
		
		customerService.addReservation(seatReservation);
	}
}
