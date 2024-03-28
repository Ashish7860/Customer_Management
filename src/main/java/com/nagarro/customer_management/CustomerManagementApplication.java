package com.nagarro.customer_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class CustomerManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerManagementApplication.class, args);
	}

	@Bean
	//@LoadBalanced   //Load balanced technique used to distribute incoming network traffic.
	public WebClient webClient() {
		return WebClient.builder().build();  // WebClient.Builder is used to create a WebClient instance.In this we're building WebClient with default settings.
        
	}
	
}
