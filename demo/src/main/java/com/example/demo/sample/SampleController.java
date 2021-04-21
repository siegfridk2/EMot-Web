package com.example.demo.sample;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SampleController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired SampleService sampleService;

	@RequestMapping("/test")
	public ModelAndView test(ModelAndView mv) {
		logger.info("this is test method1");
		
		List<SampleItem> list = sampleService.getList();
		
		mv.addObject("LIST", list);
		mv.setViewName("sample/sample");
		
		return mv;
	}
	@RequestMapping("/test2")
	public ModelAndView test2(ModelAndView mv) {
		logger.info("this is test2 method");
		
		mv.setViewName("sample/sample2");
		
		return mv;
	}
	@RequestMapping("/test3")
	public ModelAndView test3(ModelAndView mv) {
		logger.info("this is test3 method");
		
		mv.setViewName("sample/sample3");
		
		return mv;
	}
	
}
