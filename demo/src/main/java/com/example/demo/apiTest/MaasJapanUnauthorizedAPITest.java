package com.example.demo.apiTest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class MaasJapanUnauthorizedAPITest {
	
	public static void main(String[] args) {
		
//		String api_path_str = "/master_areas"; // referenced in ticketing api doc 68p
//		String api_path_str = "/master_areas"; // referenced in ticketing api doc 68p
//		String api_path_str = "/master_books"; // referenced in ticketing api doc 68p
		String api_path_str = "/master_locations"; // referenced in ticketing api doc 78p
		
		setMassJapanAPI_forUnAuth("get", api_path_str);
		
	}
	
	private static void setMassJapanAPI_forUnAuth(String api_method, String api_path_str) {
		
		String str_keyId = "Gt9D4_MQPwO2npMxqbBAyQ";
		
		StringBuffer strBufUrl = new StringBuffer();
		strBufUrl.append("https://api-platform-staging.maasjapan.net");
		strBufUrl.append(api_path_str);
//		strBufUrl.append("");
		
		String apiUrl = strBufUrl.toString();
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("authorization", "Bearer "+str_keyId);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);
		
		ResponseEntity<String> response = null;
		if(api_method.toUpperCase().equals("GET")) {
			response = restTemplate.exchange(URI.create(apiUrl), HttpMethod.GET, entity, String.class);
		} else {
			response = restTemplate.exchange(URI.create(apiUrl), HttpMethod.POST, entity, String.class);
		}
		
		
		List<?> list = new ArrayList<Map<String,Object>>();
		
		if(response.getStatusCodeValue() == 200) {
			if(response.hasBody()) {
				JSONParser jsonParser = new JSONParser();
				try {
					list = (List<?>) jsonParser.parse(response.getBody());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
