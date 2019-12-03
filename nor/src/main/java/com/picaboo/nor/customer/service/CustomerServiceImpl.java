package com.picaboo.nor.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.picaboo.nor.customer.mapper.CustomerMapper;
import com.picaboo.nor.customer.vo.Seat;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	@Autowired private CustomerMapper customerMapper;
	
	//데이터베이스의 저장된 좌석 가져오는 서비스
	@Override
	public List<Seat> getSeat(String franchiseeNo) {
		
		return customerMapper.selectSeat(franchiseeNo);
	}
}
