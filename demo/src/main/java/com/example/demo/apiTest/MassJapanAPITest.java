package com.example.demo.apiTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Base64.Encoder;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

public class MassJapanAPITest {

	public static void main(String[] args) {
		
		//set massjapan api path
//        String api_path_str = "/payments";
		String api_path_str = "/users";
//        String api_path_str = "/master_areas"; // referenced in ticketing api doc 68p
//		String api_path_str = "/master_books"; // referenced in ticketing api doc 68p
//		String api_path_str = "/master_locations"; // referenced in ticketing api doc 78p
        
        //set massjapan api body json
		JSONObject body_json = null;
		byte [] digest_byte = null;
		String digest_str = null;
		
		boolean body_exist = true;
		
		if (body_exist) {
			body_json = new JSONObject();
			body_json.put("payment_status", "paid");
			body_json.put("paid_user_id", "1e5651e5-aee5-4d62-a9d3-40edc5ca9220");
			
			//set body digest
			HashMap header_map = new HashMap();
			byte [] byteData = null;
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(body_json.toString().getBytes());
				byteData = md.digest();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Encoder en = Base64.getEncoder();
			digest_byte = en.encode(byteData);
			digest_str = "sha-256=" + new String(digest_byte);
		}
		
//        setMassJapanAPI_forDev("get", api_path_str, body_json, digest_str);
        setMassJapanAPI_forDev("post", api_path_str, body_json, digest_str);
		
	}
	
	private static void setMassJapanAPI_forDev(String api_method, String api_path_str, JSONObject body_json, String digest_str) {
		
		String str_keyId = "M4ecDuJPgkgLAn1PEw87MQ";
		String str_secret = "p8Yv5wi3us8V6S_OUJPHjDJJPjxUwL420FYDHemYFmU";
		long long_created = System.currentTimeMillis()/1000;
		String MaasJapanAPI_URL = "https://api-platform-staging.maasjapan.net";
		
		try {
			URL url = new URL(MaasJapanAPI_URL + api_path_str);
			HttpURLConnection httpConn = null;
			httpConn = (HttpURLConnection)url.openConnection();
			httpConn.setRequestMethod(api_method.toUpperCase());
			httpConn.setRequestProperty("mjp-signature", set_mjp_signature(long_created,str_keyId,str_secret,api_method,api_path_str,digest_str));
			httpConn.setRequestProperty("Content-Type","application/json");
			
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			if (body_json != null) {
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(httpConn.getOutputStream()));
				bw.write(body_json.toString());
				bw.flush();
				bw.close();
			}
			
			
			System.out.println("api kekka code    >>> "+httpConn.getResponseCode());
			System.out.println("api kekka message >>> "+httpConn.getResponseMessage());
			
			InputStream ips = httpConn.getInputStream();
			InputStreamReader ipsr = new InputStreamReader(ips, "UTF-8");
			BufferedReader bfReader = new BufferedReader(ipsr);
			
			String line = "";
			String jsonInfo = "";
			
			while((line = bfReader.readLine()) != null) {
				jsonInfo = jsonInfo + line;
				System.out.println(jsonInfo);
			}
			
//			BufferedReader br = new BufferedReader(new InputStreamReader(httpConn.getInputStream())); 
//			StringBuilder sb = new StringBuilder();
//			String line = "";
//			while((line = br.readLine()) != null) {
//				sb.append(line);
//			}
//			System.out.println(sb);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String set_mjp_signature(long long_created, String str_keyId, String str_secret, String api_method, String api_path_str, String digest_str) {
		
		String rtnStr = "";
		
		String signatire_input = "";
		signatire_input = "(request-target): " + api_method + " " +api_path_str + "\n"
				+ "(created): " + long_created;
		if (digest_str != null) {
			signatire_input = signatire_input + "\n" + "digest: " + digest_str; 
		}
		
		System.out.println("###################signatire_input###################");
		System.out.println(signatire_input);
		String hmac = "";
		try {
			hmac = calculateHMAC512(signatire_input, str_secret);
		} catch (InvalidKeyException | SignatureException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		String keyId = "keyId=";
		String algorithm = "algorithm=";
		String created = "created=";
		String headers = "headers=";
		String signature = "signature=";
		
		keyId = keyId + "" + str_keyId + ", ";
		algorithm = algorithm + ""  + "hs2019" + ", ";
		created = created + String.valueOf(long_created) + ", ";
		headers = headers + ""  + "(request-target) (created)" ;
		if (digest_str != null) {
			headers = headers + " digest" + "";
		}
		signature = ", " + signature + ""  + hmac + "";
	    
		rtnStr = keyId+algorithm+created+headers+signature;
		System.out.println("###################mjp_signature###################");
		System.out.println(rtnStr);
		
		return rtnStr;
		
	}

	private static String calculateHMAC512(String signatire_input, String str_secret) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException{
		
		String rtnStr = "";
		
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
