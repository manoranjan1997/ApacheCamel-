package com.example.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JsonProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        String content = exchange.getIn().getBody(String.class);
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String,Object> jsonMap = objectMapper.readValue(content, Map.class);

        exchange.getIn().setBody(jsonMap);

    }
}
