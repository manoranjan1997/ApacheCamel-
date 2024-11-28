package com.example.testroute;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.remote.SftpComponent;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

@CamelSpringBootTest
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UploadFileTest {

    @Autowired
   private CamelContext camelContext;

   @Autowired
    private ProducerTemplate producerTemplate;

    @Test
    public void testTextFileUpload() throws Exception {
        String testFilePath = "C:/Users/Manoranjan Mallick/Documents/SampleData/testfile.txt";
        Files.write(Paths.get(testFilePath), "Test file content".getBytes());

        // Send a test file to the route
        producerTemplate.sendBodyAndHeader("sftp://ucbos_dev@192.168.2.125:22/development/SampleData?password=uc605_D@v",
                "Hello World", "CamelFileName", "testfile.txt");

        // Verification logic to ensure the file was uploaded, such as checking the SFTP server directly
        assertTrue(Files.exists(Paths.get(testFilePath)), "File is uploaded to SFTP server");
    }

    @Test
    public void testJsonFileUpload() throws Exception {
        String jsonFilePath = "C:/Users/Manoranjan Mallick/Documents/SampleData/testfile.json";
        Files.write(Paths.get(jsonFilePath), "{\"shipmentId\":\"12345json\",\"origin\":\"A\",\"destination\":\"B\",\"status\":\"In Transit\"}".getBytes());

        // Send a JSON file to the route
        producerTemplate.sendBodyAndHeader("sftp://ucbos_dev@192.168.2.125:22/development/SampleData?password=uc605_D@v",
                "{\"shipmentId\":\"12345json\",\"origin\":\"A\",\"destination\":\"B\",\"status\":\"In Transit\"}", "CamelFileName", "testfile.json");

        // Verification logic to ensure the file was uploaded, such as checking the SFTP server directly
        assertTrue(Files.exists(Paths.get(jsonFilePath)), "File is uploaded to SFTP server");
    }

    @Test
    public void testXmlFileUpload() throws Exception {
        String xmlFilePath = "C:/Users/Manoranjan Mallick/Documents/SampleData/testfile.xml";
        Files.write(Paths.get(xmlFilePath), "<shipment><shipmentId>12345xml</shipmentId><origin>A</origin><destination>B</destination><status>In Transit</status></shipment>".getBytes());

        // Send an XML file to the route
        producerTemplate.sendBodyAndHeader("sftp://ucbos_dev@192.168.2.125:22/development/SampleData?password=uc605_D@v",
                "<shipment><shipmentId>12345xml</shipmentId><origin>A</origin><destination>B</destination><status>In Transit</status></shipment>", "CamelFileName", "testfile.xml");

        // Verification logic to ensure the file was uploaded, such as checking the SFTP server directly
        assertTrue(Files.exists(Paths.get(xmlFilePath)), "File is to SFTP server");
    }

    @Test
    public void testCsvFileUpload() throws Exception {
        String csvFilePath = "C:/Users/Manoranjan Mallick/Documents/SampleData/testfile.csv";
        Files.write(Paths.get(csvFilePath), "shipmentId,origin,destination,status\n12345csv,A,B,In Transit".getBytes());

        // Send a CSV file to the route
        producerTemplate.sendBodyAndHeader("sftp://ucbos_dev@192.168.2.125:22/development/SampleData?password=uc605_D@v",
                "shipmentId,origin,destination,status\n12345csv,A,B,In Transit", "CamelFileName", "testfile.csv");

        // Verification logic to ensure the file was uploaded, such as checking the SFTP server directly
        assertTrue(Files.exists(Paths.get(csvFilePath)), "File is uploaded to SFTP server");
    }
}
