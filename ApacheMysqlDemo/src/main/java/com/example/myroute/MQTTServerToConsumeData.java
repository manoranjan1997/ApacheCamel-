package com.example.myroute;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


public class MQTTServerToConsumeData extends RouteBuilder {

    @Override
    public void configure() throws Exception {

                from("paho:Litun?brokerUrl=tcp://test.mosquitto.org:1883")
                        .log("Received message from MQTT: ${body}")
                        .process(exchange -> {
                            String message = exchange.getIn().getBody(String.class);
                            System.out.println("Consumed message: " + message);

                        });
            }
        }

