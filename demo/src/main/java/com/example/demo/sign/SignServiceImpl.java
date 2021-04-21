package com.example.demo.sign;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.demo.apiTest.RestTemplateResponseErrorHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SignServiceImpl implements SignService {
	
	private static final Logger logger = LoggerFactory.getLogger(SignServiceImpl.class);

	@Override
	public void getToken(String code) {
//	public void getToken(Map<String, Object> tempMap) {
		// TODO Auto-generated method stub
		
		cognitoAPI_test(code);
		
//		if(tempMap.get("code") != null) {
//			cognitoAPI_test(tempMap.get("code").toString());
//		}
	}
	
	public static void cognitoAPI_test(String code) {
		
		System.out.println("service code -> "+code);
		
		StringBuffer strBufUrl = new StringBuffer();
		strBufUrl.append("https://dev-maasjapan.auth.ap-northeast-1.amazoncognito.com/ouath2/token");
		
		String apiUrl = strBufUrl.toString();
//		String apiUrl = "https://dev-maasjapan.auth.ap-northeast-1.amazoncognito.com/ouath2/token";
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("grant_type", "authorization_code&");
		params.add("client_id", "5ee2muulcbjo7nns0lgpjcgqd0456&");
		params.add("scope", "openid"); // grant_type 이 client_credentials 일때만 사용
		params.add("code", code+"&");
//		params.add("code_verifier", "CODE_VERIFIER");
		params.add("redirect_uri", "http://localhost:3000/auth/index");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String body = null;
		try {
			body = objectMapper.writeValueAsString(params);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		System.out.println("body -> "+body);
		
		HttpEntity<?> entity = new HttpEntity<>(body, headers);
		
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
		ResponseEntity<String> response = restTemplate.exchange(URI.create(apiUrl), HttpMethod.POST, entity, String.class);
		
		logger.debug("## sendMessage : {}",  response.getStatusCode());
        logger.debug("## sendMessage : {}",  response.getBody());
		
	}
	
	static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }
    static String urlEncodeUTF8(Map<?,?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                urlEncodeUTF8(entry.getKey().toString()),
                urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();       
    }

	@Override
	public void testAuth(String idToken, String accessToken) {
		// TODO Auto-generated method stub
		
		String api_path_str = "/master_areas";
		
		StringBuffer strBufUrl = new StringBuffer();
		strBufUrl.append("https://api-platform-staging.maasjapan.net");
		strBufUrl.append(api_path_str);
		String apiUrl = strBufUrl.toString();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("authorization", "Bearer "+idToken);
		headers.add("access-token", accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);
		
		
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        
		ResponseEntity<String> response = restTemplate.exchange(URI.create(apiUrl), HttpMethod.GET, entity, String.class);
		
		logger.info("## sendMessage : {}",  apiUrl);
		logger.info("## sendMessage : {}",  response.getStatusCode());
        logger.info("## sendMessage : {}",  response.getBody());
		
		
	}
}
