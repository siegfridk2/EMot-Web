package com.example.demo.sample;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import geosoft.co.kr.emot.vo.MPMResultVO;

@Service
public class SampleServiceImpl implements SampleService{

	@Override
	public List<SampleItem> getList() {
		// TODO Auto-generated method stub
		List<SampleItem> list = new ArrayList<SampleItem>();
		
		
		for(int index=0; index<10; index++) {
			SampleItem si = new SampleItem();
			si.setSeq(index);
			si.setTitle("title");
			si.setBody("body");
			
			list.add(si);
		}
		
		return list;
	}

	@Override
	public void create(MPMResultVO vo) throws Exception {
		
		String mpm_result = "";
		mpm_result = vo.getMpm_result_value();
		
	}
	
}
