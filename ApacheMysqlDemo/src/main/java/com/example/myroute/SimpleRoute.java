package com.example.myroute;

import org.apache.camel.builder.RouteBuilder;

public class SimpleRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:input")
                .log("${body}")
                .to("direct:output1");

        from("direct:output1") .log("Received message: ${body}")
                .transform().simple("Processed: ${body}");
    }
}
