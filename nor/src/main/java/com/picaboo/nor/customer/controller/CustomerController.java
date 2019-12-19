package com.picaboo.nor.customer.controller;
 
import java.text.NumberFormat;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.picaboo.nor.customer.mapper.CustomerMapper;
import com.picaboo.nor.customer.service.CustomerService;
import com.picaboo.nor.customer.vo.Customer;
import com.picaboo.nor.customer.vo.CustomerQnA;
import com.picaboo.nor.customer.vo.TotalPrice;
import com.picaboo.nor.franchisee.vo.Franchisee;
import com.picaboo.nor.franchisee.vo.FranchiseePic;
import com.picaboo.nor.franchisee.vo.Seat;

@Controller
public class CustomerController {
	@Autowired private CustomerService customerService;
	
	// 가맹점 지도 검색
	@GetMapping("/searchOnMap")
	public String searchFranchisee(HttpSession session, Model model) {
		System.out.println("searchFranchisee Get요청");
		// 세션 검사
		String customerNo = (String)session.getAttribute("memberNo");
	    if (customerNo == null) {
	    	return "redirect:/";
	    }
	    System.out.println("customerNo: " + customerNo);
	    
	    // 고객 주소 정보 조회
	    Customer customer = customerService.getCustomerAddress(customerNo);
	    System.out.println("customer: " + customer.toString());
	    // 가맹점 주소 정보 조회
	    List<Franchisee> franchiseeList = customerService.getFranchiseeAddress();
	    System.out.println("franchiseeList: " + franchiseeList.toString());
	    
	    // Model로 넘김
	    model.addAttribute("customer", customer);
	    model.addAttribute("franchiseeList", franchiseeList);
	    
		return "customer/searchOnMap";
	}
	
	// QnA 입력
	@PostMapping("/QnACustomer")
    public String addFranchisee(CustomerQnA customerQnA, HttpSession session) {
		String customerNo = (String) session.getAttribute("memberNo");
		customerQnA.setCustomerNo(customerNo);	
		//System.out.println("addFranchisee param franchisee: " + franchiseeQnA);
		customerService.addCustomerQnA(customerQnA);
        return "redirect:/customerIndex";
	}
	
	// QnA 입력 페이지 요청
	@GetMapping("/QnACustomer")
	public String QnAFranchisee(HttpSession session, Model model) {
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
	    	if (ownerNo == null) {
	    	return "redirect:/";
	    }
	    	
	    
	    //System.out.println("session memberNo: " + ownerNo);
	    model.addAttribute("memberName",session.getAttribute("memberName"));
	    return "customer/QnACustomer";
	}
	
	// QnA 입력 페이지 요청
		@GetMapping("/QnACustomerList")
		public String QnAFranchiseeList(HttpSession session, Model model) {
			// 세션 검사
			String ownerNo = (String)session.getAttribute("memberNo");
		    	if (ownerNo == null) {
		    	return "redirect:/";
		    }
		    List<CustomerQnA> list = customerService.getCustomerQnAList(ownerNo);
		    //System.out.println("session memberNo: " + ownerNo);
		    model.addAttribute("memberName",session.getAttribute("memberName"));
		    model.addAttribute("list",list);
		    return "customer/QnACustomerList";
		}
	
	//가맹점이 등록한 pc방 좌석을 고객이 확인을 하는 페이지로 get 요청
	@GetMapping("/selectCustomerSeat")
	public String customer(HttpSession session, Model model, @RequestParam("franchiseeNo")String franchiseeNo) {
		//세션값이 없으면 기본 인덱스로 이동
		if (session.getAttribute("memberNo") == null) {
			return "redirect:/";
		}
		String memberNo = (String) session.getAttribute("memberNo");
		//System.out.println(memberNo);
		model.addAttribute("memberNo", memberNo);
		
		//세션에 저장된 memberName값을 가져와 모델에 저장함
		model.addAttribute("memberName",session.getAttribute("memberName"));
		//Franchisee타입에 parameter값으로 넘어온 franchiseeNo값에 일치하는 프렌차이즈정보를 저장함
		Franchisee franchisee = customerService.getFranchisee(franchiseeNo);
		
		//Seat타입의 리스트에 franchiseeNo값에 일치하는 좌석 정보를 저장함
		List<Seat> seat = customerService.getSeat(franchiseeNo);
		//System.out.println(seat);
		
		List<FranchiseePic> picList = customerService.getFranchiseeThumbnailList(franchiseeNo);
		//System.out.println("picList" + picList);
		
		model.addAttribute("picList",picList);
		model.addAttribute("franchisee",franchisee);
		model.addAttribute("seat", seat);
		//System.out.println("cnt :"+seat);
		return "customer/selectCustomerSeat";
	}
	
	//로그인후 고객이 보는 인덱스로 get요청
	@GetMapping({"/customerIndex","/"})
	public String customerindex(HttpSession session, Model model) {
		if(session.getAttribute("memberType") != null) {
			String type = (String)session.getAttribute("memberType");
			model.addAttribute("memberName",session.getAttribute("memberName"));
			switch(type){
				case "N":
				case "C":
					//일반 고객페이지로 이동
					return "customerIndex";
				case "O":
					//점주 페이지로 이동
					return "redirect:/franchiseeIndex";
			}
		}
		//System.out.println("커스텀인덱스 세션"+session.getAttribute("memberName"));
		//세션에 저장된 memberName값을 가져와 모델에 저장함
		
		
		return "customerIndex";
	}
	
	@GetMapping("/myReservation")
	public String myReservation(HttpSession session, Model model) {
		//세션값이 없으면 기본 인덱스로 이동
		if (session.getAttribute("memberNo") == null) {
			return "redirect:/";
		}
		
		String customerNo = (String) session.getAttribute("memberNo");
		List<TotalPrice> list = customerService.getCustomerTotalPrice(customerNo);
		int total = 0;
		for(TotalPrice l:list) {
			System.out.println(l);
			total += l.getTotalPrice();
		}
		
		String totalPrice = NumberFormat.getInstance().format(total);
		System.out.println(totalPrice);
		System.out.println(total);
		model.addAttribute("totalPrice",totalPrice);
		model.addAttribute("memberName",session.getAttribute("memberName"));
		return "customer/myReservation";
	}
	
	@GetMapping("/QnACustomerDetail")
	public String QnaCustomerDetail(HttpSession session, Model model, @RequestParam("qnaNo")String qnaNo) {
		if (session.getAttribute("memberNo") == null) {
			return "redirect:/";
		}
		
		System.out.println("qnaNo:"+qnaNo);
		int no =  Integer.parseInt(qnaNo);
		CustomerQnA customer = customerService.getCustomerQnAOne(no);
		
		System.out.println("QnA:"+customer);
		
		model.addAttribute("memberName",session.getAttribute("memberName"));
		model.addAttribute("QnA",customer);
		
		return "customer/QnACustomerDetail";
	}
}
