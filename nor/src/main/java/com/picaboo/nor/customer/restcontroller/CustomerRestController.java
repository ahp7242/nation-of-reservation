 package com.picaboo.nor.customer.restcontroller;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.picaboo.nor.customer.service.CustomerService;
import com.picaboo.nor.customer.vo.*;
import com.picaboo.nor.franchisee.vo.*;

@RestController
public class CustomerRestController {
	@Autowired private CustomerService customerService;
	
	//인덱스에서 출력할 프렌차이즈 목록을 List로 반환
	@PostMapping("/getFranchiseeList")
	public Map<String, Object> getFranchiseeList(){
		List<Franchisee> franchiseeList = customerService.getFranchiseeNo();
		//System.out.println("restcontroller"+franchiseeList);
		List<FranchiseePic> franchiseePicList = customerService.getFranchiseeThumbnail();
		//System.out.println("pic list"+franchiseePicList);
		List<FranchiseeSpec> franchiseeSpecList = customerService.getFranchiseeSpecList();
		//System.out.println(franchiseeSpecList);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("franchiseeList", franchiseeList);
		map.put("franchiseePicList", franchiseePicList);
		map.put("franchiseeSpecList", franchiseeSpecList);
		return map;
	}
	
	@PostMapping("/seatReservation")
	public void seatReservation(HttpSession session,@RequestParam("seatNo")String seatNo,
								@RequestParam("date")String date,
								@RequestParam("time")String time,
								@RequestParam("franchiseeNo")String franchiseeNo) {
		String memberName = (String) session.getAttribute("memberName");
		String memberNo = (String) session.getAttribute("memberNo");
		String newDate = date + " " + time;
		
		System.out.println("memberName"+memberName);
		System.out.println("memberNo"+memberNo);
		System.out.println("예약컨트롤러"); 
		System.out.println("seatNo"+seatNo);
		System.out.println("date"+newDate);
		System.out.println("franchiseeNo"+franchiseeNo);
		
		SeatReservation seatReservation = new SeatReservation();
		seatReservation.setCustomerNo(memberNo);
		seatReservation.setCustomerName(memberName);
		seatReservation.setFranchiseeNo(franchiseeNo);
		seatReservation.setReservationDate(newDate);
		seatReservation.setSeatNo(Integer.parseInt(seatNo));
		seatReservation.setType("R");
		
		customerService.addSeatReservation(seatReservation);
	}
	
	@PostMapping("/getFoodList")
	public List<FoodInfo> getFoodList(@RequestParam("franchiseeNo")String franchiseeNo) {
		//System.out.println("getFoodList"+franchiseeNo);
		
		List<FoodInfo> list = customerService.getFoodList(franchiseeNo);
		//System.out.println("getFoodList"+list);
		return list;
	}
	
	@PostMapping("/foodReservation")
	public void foodReservation(HttpSession session,
								@RequestParam("foodNo")String foodNo,
								@RequestParam("count")String count,
								@RequestParam("date")String date,
								@RequestParam("time")String time) {
		String memberName = (String) session.getAttribute("memberName");
		String memberNo = (String) session.getAttribute("memberNo");
		String newDate = date + " " + time;
		int newCount = Integer.parseInt(count);
		int newFoodNo = Integer.parseInt(foodNo);
		
		System.out.println("memberName"+memberName);
		System.out.println("memberNo"+memberNo);
		System.out.println("foodNo"+foodNo);
		System.out.println("reservationCount"+count);
		System.out.println("date"+date);
		System.out.println("time"+time);
		System.out.println("new Date"+newDate);
		System.out.println("new Count"+newCount);
		System.out.println("new FoodNo"+newFoodNo);
		
		FoodReservation foodReservation = new FoodReservation();
		foodReservation.setCustomerName(memberName);
		foodReservation.setCustomerNo(memberNo);
		foodReservation.setReservationDate(newDate);
		foodReservation.setReservationCount(newCount);
		foodReservation.setFoodNo(newFoodNo);
		
		customerService.addFoodReservation(foodReservation);
	}
	
	@PostMapping("/getMySeatReservation")
	public List<MySeatReservationPer> getMySeatReservation(HttpSession session) {
		String customerNo = (String) session.getAttribute("memberNo");
		System.out.println("customerNo : "+customerNo);
		
		List<MySeatReservationPer> list = customerService.getMySeatReservation(customerNo);
		System.out.println("list : " + list);
		
		
		return list;
	}
	
	@PostMapping("/getMyFoodReservation")
	public List<MyFoodReservationPer> getMyFoodReservation(HttpSession session,@RequestParam("franchiseeNo")String franchiseeNo) {
		String customerNo = (String) session.getAttribute("memberNo");
		System.out.println("customerNo : " + customerNo);
		System.out.println("franchiseeNo : " + franchiseeNo);
		Franchisee franchisee = new Franchisee();
		franchisee.setFranchiseeNo(franchiseeNo);
		franchisee.setOwnerNo(customerNo);
		
		List<MyFoodReservationPer> list = customerService.getMyFoodReservation(franchisee);
		System.out.println("list : " + list);
		
		return list;
	}
}
