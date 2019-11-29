package com.picaboo.nor.franchisee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.picaboo.nor.franchisee.mapper.FranchiseeMapper;
import com.picaboo.nor.franchisee.vo.Seat;

@Service
@Transactional
public class FranchiseeServiceImpl implements FranchiseeService{
	@Autowired FranchiseeMapper franchiseeMapper;
	
	@Override
	public List<Seat> addFranchiseeSeat(Seat seat){
		System.out.println("==service==");
		System.out.println(seat);
		List<Seat> list = franchiseeMapper.insertFranchiseeSeat(seat);
		System.out.println(list);
		return list;
	}
	
//	@Override
//	public int addFranchiseeSeat(Seat seat) {
//		// TODO Auto-generated method stub
//		System.out.println("FranchiseeImpl.addFranchiseeSeat : " + seat);
//		return franchiseeMapper.insertFranchiseeSeat(seat);
//	}
}
