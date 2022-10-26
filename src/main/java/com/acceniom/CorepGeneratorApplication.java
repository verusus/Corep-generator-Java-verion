package com.acceniom;

import com.acceniom.controller.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CorepGeneratorApplication implements CommandLineRunner {
	@Autowired
	private DataManager dataManager;



	public static void main(String[] args) {
		SpringApplication.run(CorepGeneratorApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		dataManager.generateWorkbooks();

	}
}
