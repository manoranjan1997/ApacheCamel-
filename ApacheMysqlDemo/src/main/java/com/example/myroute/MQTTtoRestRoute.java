package com.example.myroute;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


public class MQTTtoRestRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // MQTT Consumer Route
        from("paho:myTopic?brokerUrl=tcp://localhost:1883")
                .log("Received message from MQTT: ${body}")
                .setHeader(Exchange.HTTP_METHOD, constant("GET")) // Or POST, depending on the API
                .to("http://localhost:9095/api/car")
                .log("Response from REST API: ${body}")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String response = exchange.getIn().getBody(String.class);
                        System.out.println("REST API response: " + response);
                    }
                });
    }
}
