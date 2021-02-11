package com.example.RelationalDb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource({"classpath:crud-dao-bean.xml"})
@SpringBootApplication
public class RelationalDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(RelationalDbApplication.class, args);
	}

}
