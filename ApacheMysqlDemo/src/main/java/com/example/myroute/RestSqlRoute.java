package com.example.myroute;


import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class RestSqlRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        rest("/say")
                .get("/query").to("direct:query")
                .get("/queryBuilder").to("direct:queryBuilder");

        from("direct:query")
                .to("sql:select * from user where id=1")
                .log("${body}")
                .transform().body();

        from("direct:queryBuilder")
                .to("sql:select * from user")
                .log("${body}")
                .transform().body();
    }
}
