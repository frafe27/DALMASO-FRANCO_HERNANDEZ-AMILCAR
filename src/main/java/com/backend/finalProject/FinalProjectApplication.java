package com.backend.finalProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.backend.finalProject.dbconnection.H2Connection;

@SpringBootApplication
public class FinalProjectApplication {


	public static void main(String[] args) {
		SpringApplication.run(FinalProjectApplication.class, args);
		H2Connection.ejecutarScriptInicial();
	}

}
