package com.example.demo.sample;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SampleService {
	List<SampleItem> getList();
}
