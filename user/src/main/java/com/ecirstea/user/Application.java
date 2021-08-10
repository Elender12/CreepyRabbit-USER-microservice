package com.ecirstea.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;

@SpringBootApplication
@EnableEurekaClient
public class Application {
	public static boolean localInstance;

	public static void main(String[] args) {

		try{
		//	localInstance = InetAddress.getLocalHost().getHostName().startsWith("ASUS");
		localInstance= true;
		}catch( Exception ex ){
			localInstance = false;
		}
		SpringApplication.run(Application.class, args);
	}
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
