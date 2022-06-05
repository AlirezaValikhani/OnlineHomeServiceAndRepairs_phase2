package org.maktab.OnlineServicesAndRepairsPhase2;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class OnlineServicesAndRepairsPhase2Application {

	public static void main(String[] args) {
		SpringApplication.run(OnlineServicesAndRepairsPhase2Application.class, args);
	}
public class farzad implements ApplicationRunner {

		BCryptPasswordEncoder b;

	public farzad(BCryptPasswordEncoder b) {
		this.b = b;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println(b.encode("aaaa"));
	}
}
}