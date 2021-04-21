package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(DemoApplication.class);

		// 스프링 부트 형태로 실행할 경우 PID 생성
		application.addListeners(new ApplicationPidFileWriter());
		application.run(args);
		
//		SpringApplication.run(DemoApplication.class, args);
	}

}
