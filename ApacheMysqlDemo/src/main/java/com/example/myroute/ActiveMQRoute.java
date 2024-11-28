package com.example.myroute;

import org.apache.camel.builder.RouteBuilder;

public class ActiveMQRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("activemq:topic:topic.HelloWorld.incoming")
                .to("activemq:queue:queue.HelloWorld")
                .log("Message routed from topic to queue: ${body}");


    }
}
