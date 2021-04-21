package com.example.demo.sign;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.NoArgsConstructor;

@Controller
@NoArgsConstructor
public class signController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired SignService signService;
	
	@RequestMapping("signForm")
	public ModelAndView signForm(ModelAndView mv) {
		logger.info("##### signForm #####");
		mv.setViewName("sign/signForm");
		
		
		return mv;
	}
	
	@RequestMapping(value = "auth/index", method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView signOAuth2(ModelAndView mv, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestBody(required=false) Object obj,
			@RequestBody(required=false) Map<String, Object> map,
			@RequestParam(value="code", required=false) String code,
			@RequestParam(value="state", required=false) String state
			) {
		
		logger.info("##### signOAuth2 #####");
		if(code != null) {
			signService.getToken(code);
		}
		
		mv.setViewName("sign/tempOAuth2");
		

		return mv;
	}
	
	@RequestMapping("authorized")
	@ResponseBody
	public ModelAndView authorized(ModelAndView mv,
			@RequestParam(value="idToken", required=false) String idToken,
			@RequestParam(value="accessToken", required=false) String accessToken) {
		logger.info("##### authorized #####");
		
		logger.info("idToken -> "+idToken);
		logger.info("accessToken -> "+accessToken);
		signService.testAuth(idToken, accessToken);
		
	
		mv.setViewName("sign/signForm");
		
		return mv;
	}
}
