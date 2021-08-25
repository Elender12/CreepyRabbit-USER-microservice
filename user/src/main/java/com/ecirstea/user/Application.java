package com.ecirstea.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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
}
