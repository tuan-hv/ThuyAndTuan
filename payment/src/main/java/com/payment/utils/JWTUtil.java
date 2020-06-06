package com.payment.utils;

import com.serviceorder.dto.AppUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

	public String getJwtTokenFromSecurityContext() {
		return ((AppUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal()).getJwtToken();
	}
}
