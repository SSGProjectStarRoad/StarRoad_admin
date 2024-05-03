package com.ssg.starroadadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class StarroadAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarroadAdminApplication.class, args);
	}

}
