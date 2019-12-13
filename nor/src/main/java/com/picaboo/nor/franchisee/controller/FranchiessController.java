package com.picaboo.nor.franchisee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.picaboo.nor.franchisee.service.FranchiseeService;
import com.picaboo.nor.franchisee.vo.FoodForm;
import com.picaboo.nor.franchisee.vo.FoodReservationList;
import com.picaboo.nor.franchisee.vo.Franchisee;
import com.picaboo.nor.franchisee.vo.FranchiseeInfoForm;
import com.picaboo.nor.franchisee.vo.FranchiseeOwner;
import com.picaboo.nor.franchisee.vo.FranchiseeQnA;
import com.picaboo.nor.franchisee.vo.Seat;

@Controller
public class FranchiessController {
	@Autowired FranchiseeService franchiseeService;
	// food리스트삭제
		@GetMapping("/delFoodReservation")
	    public String delFoodReservation(HttpSession session,@RequestParam(value="reservationNo") int reservationNo) {
			// 세션 검사
			String ownerNo = (String)session.getAttribute("memberNo");
			if (ownerNo == null) {
				return "redirect:/";
			}
			franchiseeService.delFoodReservation(reservationNo);
			System.out.println("삭제 번호"+reservationNo);
	        return "redirect:/foodReservationList";
		}
		
		// food리스트 확인
		@GetMapping("/foodReservationList")
	    public String foodReservationList(HttpSession session, Model model){
			// 세션 검사
			String ownerNo = (String)session.getAttribute("memberNo");
			if (ownerNo == null) {
				return "redirect:/";
			}
			
			String franchiseeNo = (String)session.getAttribute("franchiseeNo");
			
			List<FoodReservationList> foodReservationList = franchiseeService.getFoodReservationList(franchiseeNo);
			System.out.println("foodReservationList: " + foodReservationList);
			model.addAttribute("foodReservationList", foodReservationList);

				return "franchisee/foodReservationList";
		}
	// 가맹점 상품 추가
	@PostMapping("/addFranchiseeFood")
	public String addFranchiseeFood(FoodForm foodForm) {
		System.out.println("addFranchiseeFood POST 요청");
		
		System.out.println("foodForm: " + foodForm);
		int rows = franchiseeService.addFranchiseeFood(foodForm);
		
		if(rows < 1) 
			System.out.println("등록 실패");
		else
			System.out.println("success rows : "+ rows);
		
		return "redirect:/franchiseeFoodIndex?franchiseeNo="+foodForm.getFranchiseeNo();
	}
	
	// 가맹점 상품 추가 페이지 요청
	@GetMapping("/addFranchiseeFood")
	public String addFranchiseeFood(Model model, HttpSession session, @RequestParam(value="franchiseeNo") String franchiseeNo) {
		System.out.println("/addFranchiseeFood Get 요청");
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		if (ownerNo == null) {
			return "redirect:/";
		}
		System.out.println("session memberNo: " + ownerNo);
		// 가맹점 번호 model로 넘김
		model.addAttribute("franchiseeNo", franchiseeNo);
		
		return "franchisee/addFranchiseeFood";
	}
	
	//고객 상세정보 수정후 post요청
	@PostMapping("/modifyFranchiseeOwner")
	public String modifyFranchiseeOwner(FranchiseeOwner franchiseeOwner,HttpSession session) {
		String ownerNo = (String)session.getAttribute("memberNo");
		System.out.println("controller No:  " + ownerNo);
		franchiseeOwner.setCustomerNo((String)session.getAttribute("memberNo"));
		
		System.out.println("controller:  " + franchiseeOwner);
		franchiseeService.modifyFranchiseeOwner(franchiseeOwner);
		return "redirect:/mypageFranchisee";
	}
		
	// 마이페이지 요청
	@GetMapping("/mypageFranchisee")
    public String mypageFranchisee(HttpSession session, Model model){
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		if (ownerNo == null) {
			return "redirect:/";
		}
		//고객상세정보 확인
		FranchiseeOwner franchiseeOwner = franchiseeService.detailFranchiseeOwner(ownerNo);
		model.addAttribute("franchiseeOwner", franchiseeOwner);
		// 가맹점 리스트 가져와서 넘김
		List<Franchisee> franchiseeList = franchiseeService.getFranchiseeList(ownerNo);
		//System.out.println("index franchiseeList:" + franchiseeList);
		model.addAttribute("franchiseeList", franchiseeList);
		
		List<FranchiseeQnA> franchiseeQnaList = franchiseeService.getFranchiseeQnaList(ownerNo);
		model.addAttribute("franchiseeQnaList", franchiseeQnaList);
      return "franchisee/mypageFranchisee";
	}
		
	// 음식 상품 관리 페이지 요청
	@GetMapping("franchiseeFoodIndex")
	public String franchiseeFoodIndex(Model model, HttpSession session, @RequestParam(value="franchiseeNo") String franchiseeNo) {
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		if (ownerNo == null) {
			return "redirect:/";
		}
		System.out.println("session memberNo: " + ownerNo);
		// 가맹점 번호 model로 넘김
		model.addAttribute("franchiseeNo", franchiseeNo);
	
		// 가맹점 상품정보 가져오기
		Map<String,Object> franchiseeFood = franchiseeService.getFranchiseeFood(franchiseeNo);
		System.out.println("Controller foodPicList: " + franchiseeFood.get("foodPicList"));
		System.out.println("Controller foodList: " + franchiseeFood.get("foodList"));
		System.out.println("Controller uploadPath: " + franchiseeFood.get("uploadPath"));
		// 상품정보 model 로 넘김
		model.addAttribute("franchiseeFood", franchiseeFood);
		
		return "franchisee/franchiseeFoodIndex";
	}
	// QnA 입력
	@PostMapping("/QnAFranchisee")
    public String QnAFranchisee(FranchiseeQnA franchiseeQnA, HttpSession session) {
		String customerNo = (String) session.getAttribute("memberNo");
	    String customerMail = (String) session.getAttribute("memberEmail");
		franchiseeQnA.setCustomerNo(customerNo);
	    franchiseeQnA.setCustomerMail(customerMail);
		System.out.println("addFranchisee param franchisee: " + franchiseeQnA);
        franchiseeService.addFranchiseeQnA(franchiseeQnA);
        return "redirect:/franchiseeIndex";
	}
	
	// QnA 입력 페이지 요청
	   @GetMapping("/QnAFranchisee")
	   public String QnAFranchisee(HttpSession session,FranchiseeQnA franchiseeQnA) {
		  // 세션 검사
		  String ownerNo = (String)session.getAttribute("memberNo");
	      if (ownerNo == null) {
	         return "redirect:/";
	      }
	      System.out.println("session memberNo: " + ownerNo);
	      
	      return "franchisee/QnAFranchisee";
	   }
	
	// 가맹점 정보 수정
	@PostMapping("modifyFranchiseeInfo")
	public String modifyFranchiseeInfo(FranchiseeInfoForm franchiseeInfoForm) {
		System.out.println("modifyFranchiseeInfo POST 요청");
		System.out.println("Controller franchiseeInfoForm: " + franchiseeInfoForm);
		
		int rows = franchiseeService.modifyFranchiseeInfo(franchiseeInfoForm);
		if(rows < 0) 
			System.out.println("파일 형식 오류");
		else
			System.out.println("처리된 행의 수: " + rows);
		
		return "redirect:/detailFranchisee?franchiseeNo="+franchiseeInfoForm.getFranchiseeNo();
	}
	   
	// 가맹점 정보 수정 페이지 요청
	@GetMapping("/modifyFranchiseeInfo")
	public String modifyFranchiseeInfo(HttpSession session, Model model, @RequestParam(value="franchiseeNo") String franchiseeNo) {
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		if (ownerNo == null) {
			return "redirect:/";
		}
		System.out.println("session memberNo: " + ownerNo);
		// 가맹점 번호 넘김
		model.addAttribute("franchiseeNo", franchiseeNo);
		// Spec 목록 넘김
		Map<String, Object> spec = franchiseeService.getSpec();
		model.addAttribute("spec", spec);
		// 현재  가맹점 정보 사진, pc사양 가져와서 model로 넘김
		Map<String, Object> franchiseeInfo = franchiseeService.getFranchiseeInfo(franchiseeNo);
		System.out.println("franchiseeInfo:" + franchiseeInfo);
		model.addAttribute("franchiseeInfo", franchiseeInfo);
		return "franchisee/modifyFranchiseeInfo";
	}
	
	// FAQ 리스트 페이지
	@GetMapping("/FAQFranchisee")
	public String getFranchiseeFAQList(HttpSession session, Model model, 
								@RequestParam(value="currentPage", defaultValue="1") int currentPage,
								@RequestParam(value="searchWord", required = false) String searchWord){
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		if (ownerNo == null) {
			return "redirect:/";
		}
		System.out.println("currentPage : " + currentPage);
		System.out.println("searchWord : " + searchWord);
		int rowPerPage = 10;
		Map<String, Object> map = franchiseeService.getFranchiseeFAQ(currentPage, rowPerPage, searchWord);
		model.addAttribute("map", map);
		//System.out.println("map : " + map);
		return "franchisee/FAQFranchisee";
	}

	
	// 가맹점 정보 입력
	@PostMapping("/addFranchiseeInfo")
	public String addFranchiseeInfo(FranchiseeInfoForm franchiseeInfoForm) {
		System.out.println("addFranchiseeInfo POST 요청");
		
		System.out.println("Controller franchiseeInfoForm: " + franchiseeInfoForm);
		
		int rows = franchiseeService.addFranchiseeInfo(franchiseeInfoForm);
		
		if(rows < 0) 
			System.out.println("파일 형식 오류");
		else
			System.out.println("success rows : "+ rows);
		
		return "redirect:/detailFranchisee?franchiseeNo="+franchiseeInfoForm.getFranchiseeNo();
	}

	
	// 가맹점 정보 입력 페이지 요청
	@GetMapping("/addFranchiseeInfo")
	public String addFranchiseeInfo(HttpSession session, Model model, @RequestParam(value="franchiseeNo") String franchiseeNo) {
		System.out.println("addFranchiseeInfo Get 요청");
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		
		if (ownerNo == null) {
			return "redirect:/";
		}
		System.out.println("session memberNo: " + ownerNo);
		// 가맹점 번호 넘김
		model.addAttribute("franchiseeNo", franchiseeNo);
		// Spec 목록 넘김
		Map<String, Object> spec = franchiseeService.getSpec();
		model.addAttribute("spec", spec);
		
		return "franchisee/addFranchiseeInfo";
	}
	
	// 가맹점 좌석 삭제 요청
	@GetMapping("/removeSeat")
    public String removeSeat(@RequestParam(value="franchiseeNo") String franchiseeNo) {
		franchiseeService.removeSeat(franchiseeNo);
        return "redirect:/detailFranchisee?franchiseeNo="+franchiseeNo;
    }
	
	// 가맹점 상세 페이지 요청
	@GetMapping("/detailFranchisee")
	public String detailFranchisee(HttpSession session, @RequestParam(value="franchiseeNo") String franchiseeNo, Model model) {	
		System.out.println("detailFranchisee Get 요청");
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		
		if (ownerNo == null) {
			return "redirect:/";
		}
		System.out.println("session memberNo: " + ownerNo);
		
		// 가맹점 좌석정보 가져와서 넘김
		Franchisee franchisee = franchiseeService.getFranchiseeOne(franchiseeNo);
		List<Seat> seat = franchiseeService.getSeat(franchiseeNo);
		System.out.println("detail franchisee: " + franchisee);
		System.out.println("detail seat: " + seat);
		model.addAttribute("franchisee", franchisee);
		model.addAttribute("seat", seat);
		model.addAttribute("seatSize", seat.size());
		
		// 가맹점 정보 사진, 업로드 경로, pc사양 가져와서 model로 넘김
		Map<String, Object> franchiseeInfo = franchiseeService.getFranchiseeInfo(franchiseeNo);
		System.out.println("franchiseeInfo:" + franchiseeInfo);
		model.addAttribute("franchiseeInfo", franchiseeInfo);
		
		return "franchisee/detailFranchisee";
	}
	
	// 가맹점 인덱스 페이지 요청
	@GetMapping("/franchiseeIndex")
	public String franchiseeIndex(HttpSession session, Model model) {
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		
		if (ownerNo == null) {
			return "redirect:/";
		}
		System.out.println("session memberNo: " + ownerNo);
		
		// 가맹점 리스트 가져와서 넘김
		List<Franchisee> franchiseeList = franchiseeService.getFranchiseeList(ownerNo);
		System.out.println("index franchiseeList:" + franchiseeList);
		model.addAttribute("franchiseeList", franchiseeList);
		
		// 가맹점 썸네일 사진 가져와서 Model로 넘김
		Map<String, Object> thumbnailInfo = franchiseeService.getFranchiseeThumbnail(franchiseeList);
		System.out.println("thumbnailInfo: " + thumbnailInfo);
		model.addAttribute("thumbnailInfo", thumbnailInfo);
		
		return "franchiseeIndex";
	}
	
	// 가맹점 등록 요청
	@PostMapping("/addFranchisee")
    public String addFranchisee(Franchisee franchisee, HttpSession session) {
		System.out.println("addFranchisee param franchisee: " + franchisee);
        franchisee.setOwnerNo((String)session.getAttribute("memberNo"));
        franchiseeService.addFranchisee(franchisee);
        
        return "redirect:/franchiseeIndex";
	}
	
	// 가맹점 등록 페이지 요청
	@GetMapping("/addFranchisee")
	public String addFranchisee(HttpSession session) {
		// 세션 검사
		if (session.getAttribute("memberNo") == null) {
			return "redirect:/";
		}
		System.out.println("session memberNo: " + session.getAttribute("memberNo"));
		
		return "franchisee/addFranchisee";
	}
	
		
	// 가맹점 좌석 입력 요청
	@PostMapping("/addFranchiseeSeat")
	public String addFranchiseeSeat( @RequestParam() HashMap<String,String> seatMap) {
		System.out.println("addFranchiseeSeat Post 요청");
		
		System.out.println("addFranchiseeSeat.seatList: " + seatMap);
		franchiseeService.addFranchiseeSeat(seatMap);
		
		return "franchisee/addFranchiseeSeat";
	}
	
	// 가맹점 좌석 입력 페이지 요청
	@GetMapping("/addFranchiseeSeat") 
	public String addFranchiseeSeat(HttpSession session, Model model, @RequestParam(value="franchiseeNo") String franchiseeNo) {
		System.out.println("addFranchiseeSeat Get 요청");
		// 세션 검사
		if (session.getAttribute("memberNo") == null) {
			return "redirect:/";
		}
		// 가맹점 번호 넘김 
		model.addAttribute("franchiseeNo", franchiseeNo);
		
		return "franchisee/addFranchiseeSeat";
	}
}
