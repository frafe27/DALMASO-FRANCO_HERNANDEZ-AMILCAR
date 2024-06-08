package com.backend.finalProject;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import com.backend.finalProject.dbconnection.H2Connection;

@SpringBootApplication
public class FinalProjectApplication {


	public static void main(String[] args) {
		SpringApplication.run(FinalProjectApplication.class, args);
		//H2Connection.ejecutarScriptInicial();
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
