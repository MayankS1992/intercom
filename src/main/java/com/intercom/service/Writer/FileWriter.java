package com.intercom.service.Writer;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.intercom.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Component
@Slf4j
public class FileWriter implements IFileWriter {

    private static final String CUSTOMER_RESOURCE_FILE_NAME = "customer-info-data.json";
    private final ObjectMapper objectMapper;

    @Autowired
    public FileWriter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void write(List<Customer> customerList) {
        try {
            DataOutputStream dos = new DataOutputStream(new FileOutputStream("customers.txt"));
            String newLine = System.getProperty("line.separator");

            // Write objects to file
            for (Customer customer : customerList) {
                dos.writeBytes(customer.getName() + " " + customer.getUser_id());
                dos.writeBytes(newLine);
            }

            dos.close();

        } catch (FileNotFoundException e) {
            log.error("File not found");
        } catch (IOException e) {
            log.error("Error initializing stream");
        }
    }

    @Override
    public List<Customer> getCustomers() {
        log.info("Getting Customers");
        try {
            URL url = Resources.getResource(CUSTOMER_RESOURCE_FILE_NAME);
            JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, Customer.class);
            return objectMapper.readValue(Resources.toString(url, Charsets.UTF_8), type);
        } catch (IOException e) {
            log.error("Failed to read customers entries from file={}", CUSTOMER_RESOURCE_FILE_NAME, e);
        }
        return null;
    }
}
