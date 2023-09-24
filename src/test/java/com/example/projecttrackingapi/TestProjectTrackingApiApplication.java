package com.example.projecttrackingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestProjectTrackingApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(ProjectTrackingApiApplication::main).with(TestProjectTrackingApiApplication.class).run(args);
	}

}
