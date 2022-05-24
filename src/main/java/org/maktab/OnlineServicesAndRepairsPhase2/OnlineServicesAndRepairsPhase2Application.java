package org.maktab.OnlineServicesAndRepairsPhase2;

import org.maktab.OnlineServicesAndRepairsPhase2.controller.ViewController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class OnlineServicesAndRepairsPhase2Application /*extends SpringBootServletInitializer*/ {

	public static void main(String[] args) {
		SpringApplication.run(OnlineServicesAndRepairsPhase2Application.class, args);
	}

}
