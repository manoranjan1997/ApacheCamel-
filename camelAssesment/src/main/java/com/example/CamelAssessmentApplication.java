package com.example;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CamelAssessmentApplication {


	public static void main(String[] args) throws Exception {
		SpringApplication.run(CamelAssessmentApplication.class, args);
		CamelContext camelContext = new DefaultCamelContext();
		camelContext.start();
	}

}
