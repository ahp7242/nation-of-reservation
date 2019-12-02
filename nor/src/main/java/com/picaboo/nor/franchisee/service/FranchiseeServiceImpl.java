package com.picaboo.nor.franchisee.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.picaboo.nor.franchisee.mapper.FranchiseeMapper;
import com.picaboo.nor.franchisee.vo.Seat;

@Service
@Transactional
public class FranchiseeServiceImpl implements FranchiseeService{
	@Autowired FranchiseeMapper franchiseeMapper;

		// 좌석 입력
		@Override
		public int addFranchiseeSeat(Map<String, String> seatMap) {
		System.out.println("FranchiseeServiceImpl.addFranchiseeSeat seatMap: " + seatMap);
		

		// 입력한 좌석 수 계산
		// 맵에 seatNo, seatRow, seatCols가 3개니까 나누기 3 
		int seatCount = seatMap.size()/3;
		System.out.println("seatCount: " + seatCount);
		int rows = 0;
		for(int i=0; i<seatCount; i++) {
			Seat seat = new Seat();
		    int seatNo = Integer.parseInt(seatMap.get("seatList["+i+"][seatNo]")); 
			int seatRow = Integer.parseInt(seatMap.get("seatList["+i+"][seatRow]"));
			int seatCols = Integer.parseInt(seatMap.get("seatList["+i+"][seatCols]"));
			
			seat.setSeatNo(seatNo); 
			seat.setSeatRow(seatRow); 
			seat.setSeatCols(seatCols); 
			
			// Map에서 좌석 하나씩 가져와 insert 수행
			rows += franchiseeMapper.insertFranchiseeSeat(seat);
		}
		
		// 반복문 수행 후 결과 리턴
		if(rows >0) {
			System.out.println("가맹점 좌석 "+rows+"개 입력 성공");
		} else {
			System.out.println("가맹점 좌석 입력 실패");
		}
		
		return rows;
	}
}
