package com.example.myroute;

import org.apache.camel.builder.RouteBuilder;

public class FileTeansferRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file:input1")
                .log("${body}")
                .to("file:output1");

    }
}
