package com.example.myroute;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


public class MQTTRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // MQTT Consumer Route
//        from("paho:myTopic?brokerUrl=tcp://localhost:1883")
//                .log("Received message from MQTT: ${body}")
//                .to("log:received-mqtt-message");

     //    MQTT Producer Route
        from("timer://foo?fixedRate=true&period=1000")
                .setBody(constant("Hello from Camel"))
                .to("paho:myTopic?brokerUrl=tcp://localhost:1883");
    }
}
