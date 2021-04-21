package com.example.demo.sign;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SignService {
//	void getToken(Map<String, Object> tempMap);
	void getToken(String code);
	void testAuth(String idToken, String accessToken);
}
