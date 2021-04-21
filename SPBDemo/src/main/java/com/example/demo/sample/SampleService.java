package com.example.demo.sample;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import geosoft.co.kr.emot.vo.MPMResultVO;

@Transactional
public interface SampleService {
	
	List<SampleItem> getList();
	
	//set vo(mpmresultvo)
	void create(MPMResultVO vo) throws Exception;
}
