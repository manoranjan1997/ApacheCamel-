package com.example.myroute;

import org.apache.camel.builder.RouteBuilder;

public class RestConstantRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");
        rest("/say") .get("/constant")
                .to("direct:constantResponse");
        from("direct:constantResponse")
                .transform().constant("This is a constant response") .log("${body}");
    }
}
