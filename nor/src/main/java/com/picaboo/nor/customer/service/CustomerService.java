package com.picaboo.nor.customer.service;

import java.util.List;

import com.picaboo.nor.customer.vo.Seat;

public interface CustomerService {
	public List<Seat> getSeat(String franchiseeNo);
}
