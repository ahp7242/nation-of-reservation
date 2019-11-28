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
	
	@Override
	public List<Seat> getSeat(String franchiseeNo) {
		
		return customerMapper.selectSeat(franchiseeNo);
	}
}
