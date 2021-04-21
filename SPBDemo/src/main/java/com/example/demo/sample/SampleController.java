package com.example.demo.sample;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import geosoft.co.kr.emot.vo.MPMResultVO;

@Controller
public class SampleController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired SampleService sampleService;

	@RequestMapping("/mpm3")
	public ModelAndView mpm3(ModelAndView mv) {
		
		logger.info("this is mpm skill test method");
		
		List<SampleItem> list = sampleService.getList();
		
		mv.addObject("LIST", list);
		mv.setViewName("mpm/mpm3");
		
		return mv;
	}
	
	@RequestMapping("/mpm2")
	public ModelAndView test(ModelAndView mv) {
		
		logger.info("this is mpm skill test method");
		
		List<SampleItem> list = sampleService.getList();
		
		mv.addObject("LIST", list);
		mv.setViewName("mpm/mpm2");
		
		return mv;
	}
	
	@RequestMapping("/mpm")
	public ModelAndView mpmtest(ModelAndView mv) {
		
		logger.info("this is mpm skill test method");
		
		List<SampleItem> list = sampleService.getList();
		
		mv.addObject("LIST", list);
		mv.setViewName("mpm/mpm");
		
		return mv;
	}
	
	@RequestMapping(value="/mpmres", method=RequestMethod.GET)
	public ModelAndView mpmresult(ModelAndView mv,@RequestParam("res")String mpmres) {
		
		logger.info("this is mpm skill kekka test method");
		
		mv.addObject("mpmres",mpmres);
		mv.setViewName("mpm/mpm_result");
		
		return mv;
	}
	
	
}
