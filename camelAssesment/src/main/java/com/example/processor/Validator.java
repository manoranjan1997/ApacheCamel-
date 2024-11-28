package com.example.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class Validator implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        Map<String, Object> body = exchange.getIn().getBody(Map.class);

        if (!Validator.validate(body)) {

            exchange.getIn().setHeader("invalidRecord", true);
        }
    }

    public static boolean validate(Map<String, Object> data) {
        return data.containsKey("shipmentId") &&
                data.containsKey("origin") &&
                data.containsKey("destination") &&
                data.containsKey("status")
                ;
    }
}
