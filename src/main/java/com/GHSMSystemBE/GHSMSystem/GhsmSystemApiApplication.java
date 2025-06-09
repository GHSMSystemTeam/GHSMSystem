package com.GHSMSystemBE.GHSMSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GhsmSystemApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GhsmSystemApiApplication.class, args);
	}

	// swagger page http://localhost:8080/swagger-ui/index.html
	//H2 in memory database link  http://localhost:8080/h2-console
}
