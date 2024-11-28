package com.example.myroute;

import org.apache.camel.builder.RouteBuilder;

public class SftpRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("sftp://ucbos_dev@192.168.2.125:22/development/CarOUT?password=uc605_D@v")
                .log("Downloaded file: ${header.CamelFileName}")
                .to("file:output");
    }
}
