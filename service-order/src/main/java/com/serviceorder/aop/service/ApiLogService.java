package com.serviceorder.aop.service;

import com.serviceorder.entities.ApiLog;
import com.serviceorder.repositories.ApiLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiLogService {

	@Autowired
	private ApiLogRepository apiLogRepository;
	
	public List<ApiLog> list() {
        return apiLogRepository.findAll();
    }
	
	public void saveApiLog(ApiLog apiLog) {
		apiLogRepository.save(apiLog);
	}
}
