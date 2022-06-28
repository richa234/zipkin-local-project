package com.zipkin.service.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceRegistryMsV1Application {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRegistryMsV1Application.class, args);
	}

}
