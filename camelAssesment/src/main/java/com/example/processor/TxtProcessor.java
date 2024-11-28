package com.example.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TxtProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String content = exchange.getIn().getBody(String.class);
        String[] lines = content.split("\n");

        Map<String, Object> txtMap = new HashMap<>();
        for (String line : lines) {
            String[] keyValue = line.split(":");
            txtMap.put(keyValue[0].trim(), keyValue[1].trim());
        }
        exchange.getIn().setBody(txtMap);
    }
}
