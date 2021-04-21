package com.example.demo.apiTest;

import java.net.URI;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class MaasJapanDeveloperAPITest {
	
	private static final Logger logger = LoggerFactory.getLogger(MaasJapanDeveloperAPITest.class);
	
	public static void main(String[] args) {
//		String api_path_str = "/frames";
//		String api_path_str = "/book_groups";
		String api_path_str = "/master_books"; // referenced in ticketing api doc 68p
//		String api_path_str = "/master_areas";
//		String api_path_str = "/users";
//		String api_path_str = "/payments";
//		String api_path_str = "/master_corporations";
//		String api_path_str = "/emails";
		
		
		setMassJapanAPI_forDev("get", api_path_str);
//		setMassJapanAPI_forDev("post", api_path_str);
	}
	
	private static void setMassJapanAPI_forDev(String api_method, String api_path_str) {
		long long_created = System.currentTimeMillis()/1000;
		
		StringBuffer strBufUrl = new StringBuffer();
		strBufUrl.append("https://api-platform-staging.maasjapan.net");
		strBufUrl.append(api_path_str);
		String apiUrl = strBufUrl.toString();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("mjp-signature", set_mjp_signature(long_created,api_method,api_path_str));
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("id", "00000000-0000-4000-0000-000000000000");
		params.add("pool_id", "fugafuga");
		params.add("name", "Google_hogehoge");
		params.add("apps", "emot");
		HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);
		
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        
		ResponseEntity<Object> response = null;
		
		if(api_method.toUpperCase().equals("GET")) {
			response = restTemplate.exchange(URI.create(apiUrl), HttpMethod.GET, entity, Object.class);
		} else {
			response = restTemplate.exchange(URI.create(apiUrl), HttpMethod.POST, entity, Object.class);
		}
		
		logger.debug("## sendMessage : {}",  response.getStatusCode());
        logger.debug("## sendMessage : {}",  response.getBody());
        
		if(response.getStatusCodeValue() == 200) {
			if(response.hasBody()) {
				System.out.println(response.getBody());
			}
		}
	}
	
	private static String set_mjp_signature(long long_created, String api_method, String api_path_str) {
		String str_keyId = "M4ecDuJPgkgLAn1PEw87MQ";
		
		String signatire_input = "";
		signatire_input = "(request-target): " + api_method + " " +api_path_str + "\n" 
				+ "(created): " + long_created;

		String hmac = "";
		try {
			hmac = calculateHMAC512(signatire_input);
		} catch (InvalidKeyException | SignatureException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("keyId="+str_keyId);
		strBuf.append(", ");
		strBuf.append("algorithm=");
		strBuf.append("hs2019");
		strBuf.append(", ");
		strBuf.append("created=");
		strBuf.append(long_created);
		strBuf.append(", ");
		strBuf.append("headers=");
		strBuf.append("(request-target) (created), ");
		strBuf.append("signature=");
		strBuf.append(hmac);
		strBuf.append("");
		return strBuf.toString();
	}
	
	private static String calculateHMAC512(String signatire_input) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException{
		String str_secret = "p8Yv5wi3us8V6S_OUJPHjDJJPjxUwL420FYDHemYFmU";
		SecretKeySpec secretKeySpec = new SecretKeySpec(str_secret.getBytes(), "HmacSHA512");
		Mac mac = Mac.getInstance("HmacSHA512");
	    mac.init(secretKeySpec);
	    byte [] encryptbyte = mac.doFinal(signatire_input.getBytes()); 
	    return toEncode64(encryptbyte);
	}
	
	private static String toEncode64(byte[] bytes) {
		Encoder en = Base64.getEncoder();
		byte [] digest_byte = en.encode(bytes);
	    return new String(digest_byte);
	}

}
