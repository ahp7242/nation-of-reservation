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
import com.picaboo.nor.franchisee.vo.FoodStatement;
import com.picaboo.nor.franchisee.vo.Franchisee;
import com.picaboo.nor.franchisee.vo.FranchiseeForm;
import com.picaboo.nor.franchisee.vo.FranchiseeInfoForm;
import com.picaboo.nor.franchisee.vo.FranchiseeOwner;
import com.picaboo.nor.franchisee.vo.FranchiseeQnA;
import com.picaboo.nor.franchisee.vo.Seat;
import com.picaboo.nor.franchisee.vo.SeatReservationList;
import com.picaboo.nor.franchisee.vo.TodayStatement;
import com.picaboo.nor.franchisee.vo.TotalStatement;

@Controller
public class FranchiseeController {
	@Autowired FranchiseeService franchiseeService;
	
	//QnA디테일
	@GetMapping("/QnAFranchiseeDetail")
    public String QnAFranchiseeDetail(HttpSession session,
    									Model model,
    									@RequestParam(value="qnaNo") int qnaNo) {
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		if (ownerNo == null) {
			return "redirect:/";
		}
		System.out.println("a"+qnaNo);
		List<FranchiseeQnA> franchiseeQnAselect = franchiseeService.getQnaDetail(qnaNo);
		
		System.out.println(franchiseeQnAselect);
		model.addAttribute("franchiseeQnAselect",franchiseeQnAselect);
		
        return "franchisee/QnAFranchiseeDetail";
	}	
	// 좌석 예약 리스트 확인
	@GetMapping("/cancelSeatReservation")
    public String cancelSeatReservation(HttpSession session, 
    		SeatReservationList seatReservationList,
    		@RequestParam(value="franchiseeNo") String franchiseeNo,
    		@RequestParam(value="seatNo") int seatNo) {
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		if (ownerNo == null) {
			return "redirect:/";
		}
		seatReservationList.setFranchiseeNo(franchiseeNo);
		seatReservationList.setSeatNo(seatNo);
		
		franchiseeService.cancelSeatReservation(seatReservationList);
        return "redirect:/seatReservationList";
	}	
	
	// 좌석 예약 리스트 확인
	@GetMapping("/delSeatReservation")
    public String delSeatReservation(HttpSession session, 
    		SeatReservationList seatReservationList,
    		@RequestParam(value="franchiseeNo") String franchiseeNo,
    		@RequestParam(value="seatNo") int seatNo) {
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		if (ownerNo == null) {
			return "redirect:/";
		}
		seatReservationList.setFranchiseeNo(franchiseeNo);
		seatReservationList.setSeatNo(seatNo);
		
		franchiseeService.delSeatReservation(seatReservationList);
        return "redirect:/seatReservationList";
	}
	
	// 좌석 예약 리스트 확인
	@GetMapping("/seatReservationList")
    public String seatReservationList(HttpSession session, Model model){
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		if (ownerNo == null) {
			return "redirect:/";
		}
		
		String franchiseeNo = (String)session.getAttribute("franchiseeNo");
		
		System.out.println("컨트롤러  "+franchiseeNo);
		List<SeatReservationList> seatReservationList = franchiseeService.getSeatReservationList(franchiseeNo);
		System.out.println("seatReservationList: " + seatReservationList);
		model.addAttribute("seatReservationList", seatReservationList);

			return "franchisee/seatReservationList";
	}
	

	// 상품에따른 가맹점별 매출 현황
	@GetMapping("/totalStatement")
	public String totalStatement(HttpSession session, Model model,TotalStatement totalStatement){
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		if (ownerNo == null) {
			return "redirect:/";
		}	
		System.out.println("get요청");
		List<TotalStatement> totalStatement1 = franchiseeService.getTotalStatementList(ownerNo);

		model.addAttribute("totalStatement1", totalStatement1);
		System.out.println(totalStatement1);
			return "franchisee/totalStatement";
	}	
	
	// 오늘 매출 가맹점별 매출 현황
	@GetMapping("/todayStatement")
	public String todayStatement(HttpSession session, Model model,TodayStatement todayStatement){
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		if (ownerNo == null) {
			return "redirect:/";
		}	
		System.out.println("get요청");
		List<TodayStatement> todayStatement1 = franchiseeService.getTodayStatementList(ownerNo);

		model.addAttribute("todayStatement1", todayStatement1);
		System.out.println(todayStatement1);
			return "franchisee/todayStatement";
	}
	// 음식 예약 통계 확인
	@GetMapping("/foodStatement")
	public String foodStatement(HttpSession session, Model model,FoodStatement foodStatement){
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		if (ownerNo == null) {
			return "redirect:/";
		}	
		List<FoodStatement> foodStatement1 = franchiseeService.getFoodfoodStatementList(foodStatement);

		model.addAttribute("foodStatement1", foodStatement1);
		
		
			return "franchisee/foodStatement";
	}
	
	// 가맹점 수정
	@PostMapping("/modifyFranchiseeFood")
	public String modifyFranchiseeFood(FoodForm foodForm) {
		System.out.println("modifyFranchiseeFood POST 요청");
		System.out.println("foodForm: " + foodForm);
		
		franchiseeService.modifyFranchiseeFood(foodForm);
		
		return "redirect:/franchiseeFoodIndex?franchiseeNo=" + foodForm.getFranchiseeNo();
	}
	
	// 가맹점 상품 수정 페이지 요청
	@GetMapping("/modifyFranchiseeFood")
	public String modifyFranchiseeFood(HttpSession session, Model model, @RequestParam(value="foodNo") int foodNo) {
		System.out.println("modifyFranchiseeFood Get 요청");
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		if (ownerNo == null) {
			return "redirect:/";
		}
		System.out.println("session memberNo: " + ownerNo);
		// 원래 상품 정보
		System.out.println("foodNo: " + foodNo);
		Map<String, Object> foodInfo = franchiseeService.getFranchiseeFoodOne(foodNo);
		System.out.println("foodInfo: " + foodInfo.toString());
		model.addAttribute("foodInfo", foodInfo);
		return "franchisee/modifyFranchiseeFood";
	}
	
	// 가맹점 상품 삭제 요청
	@GetMapping("/removeFranchiseeFood") 
	public String removeFranchiseeFood(@RequestParam(value="franchiseeNo") String franchiseeNo, @RequestParam(value="foodNo") int foodNo) {
		System.out.println("removeFranchiseeFood Get 요청");
		System.out.println("removeFranchiseeFood param franchiseeNo: " + franchiseeNo);
		System.out.println("removeFranchiseeFood param foodNo: " + foodNo);
		
		int rows = franchiseeService.removeFranchiseeFood(foodNo);
		System.out.println("성공한 수: " + rows);
		
		return "redirect:/franchiseeFoodIndex?franchiseeNo=" + franchiseeNo;
	}
	
	// 음식 예약 리스트 삭제
	@GetMapping("/delFoodReservation")
    public String delFoodReservation(HttpSession session, @RequestParam(value="reservationNo") int reservationNo) {
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		if (ownerNo == null) {
			return "redirect:/";
		}
		franchiseeService.delFoodReservation(reservationNo);
		System.out.println("삭제 번호"+reservationNo);
		
        return "redirect:/foodReservationList";
	}
	// 음식 예약 리스트 삭제
	@GetMapping("/cancelFoodReservation")
    public String cancelFoodReservation(HttpSession session, @RequestParam(value="reservationNo") int reservationNo) {
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		if (ownerNo == null) {
			return "redirect:/";
		}
		franchiseeService.cancelFoodReservation(reservationNo);
		System.out.println("삭제 번호"+reservationNo);
		
        return "redirect:/foodReservationList";
	}
	
	
	
	// 음식 예약 리스트 확인
	@GetMapping("/foodReservationList")
    public String foodReservationList(HttpSession session, Model model){
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		if (ownerNo == null) {
			return "redirect:/";
		}
		
		String franchiseeNo = (String)session.getAttribute("franchiseeNo");
		System.out.println("ㅇㅇ: " + franchiseeNo);
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
		public String franchiseeFoodIndex(Model model, HttpSession session,
				@RequestParam(value="franchiseeNo") String franchiseeNo, @RequestParam(value="foodCategory", required = false) String foodCategory) {
			System.out.println("franchiseeFoodIndex Get 요청");
			// 세션 검사
			String ownerNo = (String)session.getAttribute("memberNo");
			if (ownerNo == null) {
				return "redirect:/";
			}
			System.out.println("session memberNo: " + ownerNo);
			// 가맹점 번호 model로 넘김
			model.addAttribute("franchiseeNo", franchiseeNo);
		
			// 가맹점 상품정보 가져오기
			Map<String,Object> franchiseeFood = franchiseeService.getFranchiseeFood(franchiseeNo, foodCategory);
			System.out.println("Controller foodPicList: " + franchiseeFood.get("foodPicList"));
			System.out.println("Controller foodList: " + franchiseeFood.get("foodList"));
			System.out.println("Controller uploadPath: " + franchiseeFood.get("uploadPath"));
			System.out.println("Controller franchiseeNo: " + franchiseeFood.get("franchiseeNo"));
			// 상품정보 model 로 넘김
			model.addAttribute("franchiseeFood", franchiseeFood);
			model.addAttribute("foodCategory", foodCategory);
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
			System.out.println("파일 업로드 안 됨");
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
		session.setAttribute("franchiseeNo", franchiseeNo);
		
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
		System.out.println("franchiseeIndex Get 요청");
		// 세션 검사
		String ownerNo = (String)session.getAttribute("memberNo");
		
		if (ownerNo == null) {
			return "redirect:/";
		}
		System.out.println("session memberNo: " + ownerNo);
		
		// 가맹점 리스트 가져와서 넘김
		List<Franchisee> franchiseeList = franchiseeService.getFranchiseeList(ownerNo);
		System.out.println("Controller franchiseeList:" + franchiseeList);
		model.addAttribute("franchiseeList", franchiseeList);
		
		// 가맹점 썸네일 사진 가져와서 Model로 넘김
		Map<String, Object> thumbnailInfo = franchiseeService.getFranchiseeThumbnail(franchiseeList);
		System.out.println("thumbnailInfo: " + thumbnailInfo);
		model.addAttribute("thumbnailInfo", thumbnailInfo);
		
		return "franchiseeIndex";
	}
	
	// 가맹점 등록 요청
	@PostMapping("/addUnverifiedFranchisee")
    public String addFranchisee(FranchiseeForm franchiseeForm, HttpSession session) {
		
		System.out.println("addFranchisee param franchiseeForm: " + franchiseeForm);
		
		franchiseeForm.setOwnerNo((String)session.getAttribute("memberNo"));
        
        franchiseeService.addUnverifiedFranchisee(franchiseeForm);
        
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
