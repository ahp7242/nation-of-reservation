package com.picaboo.nor.franchisee.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.picaboo.nor.franchisee.mapper.FranchiseeMapper;
import com.picaboo.nor.franchisee.vo.Franchisee;
import com.picaboo.nor.franchisee.vo.Seat;

@Service
@Transactional
public class FranchiseeServiceImpl implements FranchiseeService{
	@Autowired FranchiseeMapper franchiseeMapper;
		
	// 가맹점 등록
	@Override
	public int addFranchisee(Franchisee franchisee) {
		// 가맹점 번호: "유형" + 숫자7자리
		// 마지막 가맹점번호 저장
		String seqNo = franchiseeMapper.selectFranchiseeSeq();
		System.out.println("seqNo: " + seqNo);
		
		// NULL값 처리
		if(seqNo == null) {
			seqNo = String.format("%07d", 0);
		}
		
		// 증가시키기 위한 숫자만 잘라서 저장
		seqNo = seqNo.substring(1);
		// 숫자 증가
		seqNo = String.format("%07d", (Integer.parseInt(seqNo)+1));
		// 증가 후 유형문자 추가 
		String nextNo = "F"+seqNo;
		System.out.println("nextNo: " + nextNo);
		
		// 다음 가맹점 번호 객체에 저장
		franchisee.setFranchiseeNo(nextNo);
		// 다음 가맹점 번호로 insert 수행
		franchiseeMapper.insertFranchisee(franchisee);
		// 마지막 가맹점 번호 갱신
		franchiseeMapper.updateFranchiseeSeq(nextNo);
		return 0;
	}
	
		
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
