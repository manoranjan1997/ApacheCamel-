package com.example.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CsvProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String content = exchange.getIn().getBody(String.class);
        String[] lines = content.split("\n");
        String[] headers = lines[0].split(",");
        String[] values = lines[1].split(",");

        Map<String, Object> csvMap = new HashMap<>();
        for (int i = 0; i < headers.length; i++) {
            String cleanHeader = headers[i].trim().replaceAll("^'|'$", "");
            String cleanValue = values[i].trim().replaceAll("^'|'$", "");
            csvMap.put(cleanHeader, cleanValue);
        }
        exchange.getIn().setBody(csvMap);
    }
}
