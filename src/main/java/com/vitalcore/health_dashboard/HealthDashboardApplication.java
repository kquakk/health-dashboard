package com.vitalcore.health_dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class HealthDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthDashboardApplication.class, args);
	}

}
