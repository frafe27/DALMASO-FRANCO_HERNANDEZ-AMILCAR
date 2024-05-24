package com.backend.finalProject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.backend.finalProject.dbconnection.H2Connection;

@SpringBootApplication
public class FinalProjectApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(FinalProjectApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FinalProjectApplication.class, args);
		H2Connection.ejecutarScriptInicial();
		LOGGER.info("Final Project Running..");
	}

}
