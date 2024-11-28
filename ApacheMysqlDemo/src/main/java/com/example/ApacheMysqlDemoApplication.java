package com.example;

import com.example.myroute.*;
import org.apache.camel.CamelContext;
import org.apache.camel.component.activemq.ActiveMQComponent;
import org.apache.camel.component.paho.PahoComponent;
import org.apache.camel.component.sql.SqlComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SpringBootApplication
public class ApacheMysqlDemoApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ApacheMysqlDemoApplication.class, args);

		// Configure SQL component
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		SqlComponent sqlComponent = new SqlComponent();
		driverManagerDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/litun");
		driverManagerDataSource.setUsername("root");
		driverManagerDataSource.setPassword("root");
		sqlComponent.setDataSource(driverManagerDataSource);

		// Configure Camel context
		CamelContext camelContext = new DefaultCamelContext();
		camelContext.addComponent("sql", sqlComponent);

		// Configure ActiveMQ component
		ActiveMQComponent activeMQComponent = ActiveMQComponent
				.activeMQComponent("tcp://localhost:61616");
		camelContext.addComponent("activemq", activeMQComponent);



		camelContext.addRoutes(new SftpRoute());
//		camelContext.addRoutes(new RestConstantRoute());
//		camelContext.addRoutes(new SimpleRoute());
//		camelContext.addRoutes(new FileTeansferRoute());
//		camelContext.addRoutes(new MQTTRoute());
//		camelContext.addRoutes(new MQTTtoRestRoute());
//		camelContext.addRoutes(new ActiveMQRoute());
//		camelContext.addRoutes(new RestSqlRoute());
//		camelContext.addRoutes(new RestConsumerRouter());
//		camelContext.addRoutes(new MQTTServerToConsumeData());

		// Start Camel context

		camelContext.start();

		//for SimpleRoute
		camelContext.createProducerTemplate()
				.sendBody("direct:input", "Manoranjan Mallick");

	}


}