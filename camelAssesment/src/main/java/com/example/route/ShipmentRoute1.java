package com.example.route;

import com.example.processor.CsvProcessor;
import com.example.processor.JsonProcessor;
import com.example.processor.TxtProcessor;
import com.example.processor.XmlProcessor;
import com.example.processor.Validator;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Map;

import static org.apache.camel.support.processor.idempotent.MemoryIdempotentRepository.memoryIdempotentRepository;

@Component
public class ShipmentRoute1 extends RouteBuilder {

    @Autowired
    private JsonProcessor jsonProcessor;

    @Autowired
    private Validator validator;

    @Autowired
    private TxtProcessor txtProcessor;

    @Autowired
    private XmlProcessor xmlProcessor;

    @Autowired
    private CsvProcessor csvProcessor;


    @Override
    public void configure() throws Exception {
        // Error Handling and Retry Mechanism with Detailed Logging
        //Configures a dead-letter channel
        errorHandler(deadLetterChannel("log:dead")
               //Specifies the maximum number of attempts to redeliver the message
                .maximumRedeliveries(3)
                .redeliveryDelay(1000)  // Sets Initial delay of 1 second
                .backOffMultiplier(2)  // Applies exponential backoff to the redelivery delay
                .retryAttemptedLogLevel(LoggingLevel.WARN)//Configures the logging level for retry attempts.
                .logHandled(true)//Logs a message when the error has been handled successfully.
                .logExhaustedMessageHistory(true)//Logs the entire message history when retries are exhausted,
                //Defines a custom callback for each redelivery attempt
                .onRedelivery(exchange -> {
                    //Retrieves the retry count using
                    int retryCount = exchange.getIn().getHeader("CamelRedeliveryCounter", Integer.class);
                   //Retrieves the exception that caused the failure using
                    Exception causedByException = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                  //Logs a warning message indicating the retry attempt number and the cause of the failure.
                   log.warn("Retry attempt #{} due to: {}", retryCount, causedByException.getMessage());
                }));

        // SFTP Route with Transaction Support
        from("sftp://ucbos_dev@192.168.2.125:22/development/SampleData?password=uc605_D@v")
                .transacted()
                .idempotentConsumer(header("CamelFileName"), memoryIdempotentRepository())
                .log("Downloaded file: ${header.CamelFileName}")
                .choice()
                    .when(header("CamelFileName").endsWith(".json"))
                        .process(jsonProcessor)
                        .process(validator)
                        .choice()
                            .when(header("invalidRecord").isEqualTo(true))
                                .log("Invalid record: ${body}")
                                .stop()
                            .otherwise()
                                  .toD("sql:INSERT INTO Shipment  VALUES ('${body[shipmentId]}','${body[origin]}','${body[destination]}','${body[status]}','${body[itemId]}','${body[description]}','${body[quantity]}')")
                            .end()
                        .endChoice()
                    .when(header("CamelFileName").endsWith(".xml"))
                        .process(xmlProcessor)
                        .process(validator)
                        .choice()
                            .when(header("invalidRecord").isEqualTo(true))
                                .log("Invalid record: ${body}")
                                .stop()
                            .otherwise()
                                 .toD("sql:INSERT INTO Shipment VALUES ('${body[shipmentId]}','${body[origin]}','${body[destination]}','${body[status]}','${body[itemId]}','${body[description]}','${body[quantity]}')")
                            .end()
                        .endChoice()
                    .when(header("CamelFileName").endsWith(".csv"))
                        .process(csvProcessor)
                        .process(validator)
                        .choice()
                            .when(header("invalidRecord").isEqualTo(true))
                               .log("Invalid record: ${body}")
                               .stop()
                           .otherwise()
                                .toD("sql:INSERT INTO Shipment  VALUES ('${body[shipmentId]}','${body[origin]}','${body[destination]}','${body[status]}','${body[itemId]}','${body[description]}','${body[quantity]}')")
                               .end()
                        .endChoice()
                    .when(header("CamelFileName").endsWith(".txt"))
                        .process(txtProcessor)
                        .process(validator)
                        .choice()
                            .when(header("invalidRecord").isEqualTo(true))
                                .log("Invalid record: ${body}")
                                .stop()
                            .otherwise()
                                 .toD("sql:INSERT INTO Shipment VALUES ('${body[shipmentId]}','${body[origin]}','${body[destination]}','${body[status]}','${body[itemId]}','${body[description]}','${body[quantity]}')")
                            .end()
                        .endChoice()
                    .otherwise()
                        .log("Unsupported file type: ${header.CamelFileName}")
                    .end()
                    .log("Processing completed for file: ${header.CamelFileName}")
                .end();


    }
}
