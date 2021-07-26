package com.fisglobal.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SpringBootSubscriptionSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSubscriptionSampleApplication.class, args);
	}

}
