package com.picaboo.nor.membership.service;

import com.picaboo.nor.membership.vo.*;

public interface MembershipService {
	public int addMembership(Membership membership);
	public Membership loginMembership(Login login);
}
