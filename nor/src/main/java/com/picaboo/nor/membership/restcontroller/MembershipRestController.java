package com.picaboo.nor.membership.restcontroller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.picaboo.nor.membership.service.MembershipService;

@RestController
public class MembershipRestController {
	@Autowired private MembershipService membershipService;
	
	@PostMapping("/overlap")
	public int overlapCustomerId(String customerId) {
		//System.out.println("restcontroller"+customerId);
		
		//아이디 중복확인시 리턴값을 count에 저장후 리턴
		int count = membershipService.getOverlapCustomerId(customerId);
		//System.out.println("count" + count);
		return count;
	}
	
	@PostMapping("/naverlogin")
	public void dataInsert(HttpSession session,@RequestParam("email") String email,
					   @RequestParam("name") String name,
					   @RequestParam("age") String age,
					   @RequestParam("birthday") String birthday,
					   @RequestParam("gender") String gender) {
		
		session.setAttribute("memberNo", email);
		session.setAttribute("age", age);
		session.setAttribute("memberName", name);
		session.setAttribute("gender", gender);
		session.setAttribute("birthday", birthday);
		session.setAttribute("memberType", "N");
		
		/*
		 * System.out.println("이메일 :"+session.getAttribute("memberNo"));
		 * System.out.println("나이 :"+age); System.out.println("성별 :"+gender);
		 * System.out.println("생일 :"+birthday); System.out.println("name:" + name);
		 * System.out.println("타입" + session.getAttribute("memberType"));
		 */
	}
}
